#!groovy
// Loads ALM Pipeline library
// see https://gitlab.alm.gsnetcloud.corp/serenity-alm/pipeline-library/wikis/home
@Library('global-alm-pipeline-library') _
def artifactUrl
def pomMap
def credId
def ose3Project = scib-sarq
def ose3TokenID = "OPENSHIFT_TOKEN_CREDENTIAL_ID"
def ose3Region = https://api.boae.paas.gsnetcloud.corp:8443
//List of approvers authorized to deploy in the PRE environment
def approversPre='implantaciongcb-developer,implantaciongcb-technical-lead,implantaciongcb-product-owner'
//List of approvers authorized to deploy in the PRO environment
def approversPro='implantaciongcb-developer,implantaciongcb-technical-lead,implantaciongcb-product-owner'
pipeline {
    agent {
        node {
            // set maven slave
            label 'maven'
        }
    }
    environment{
        // Release branch name
        MASTER_BRANCH = 'master'
        // CI Branch name
        INTEGRATION_BRANCH = 'develop'
    }
    parameters {
        booleanParam(name: 'IS_RELEASE', defaultValue: false, description: 'Is a release build?')
        booleanParam(name: 'DEPLOY_ONLY', defaultValue: false, description: 'Saltar los pasos de construccion y publicacion y solo desplegar la version indicada en DEPLOY_VERSION.')
        string(name: 'DEPLOY_VERSION', defaultValue: 'LATEST', description: 'Solo valida con DEPLOY_ONLY. Publica la version indicada en el entorno.')
        choice(choices: 'DEV\nPRE-PRO', name: 'DEPLOY_ENVIRONMENT', description: 'Solo valida con DEPLOY_ONLY. Despliega en el entorno indicado.')
    }
    options {
        gitLabConnection(com.santander.alm.pipeline.git.Git.SERENITY_GITLAB_CONNECTION_NAME)
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timeout(time: 60, unit: 'DAYS')
    }
    triggers {
        gitlab(triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All')
    }
    stages {
        stage('Build') {
            when {
                not { expression { return params.DEPLOY_ONLY } }
                }
            steps {
                // Sets gitlab build status to 'running'
                updateGitlabCommitStatus name: 'build', state: 'running'
                // reads the pom and changes the build name with it
                script {
                    // reads the maven pom
                    pomMap = almMavenReadInfoFromPom()
                    if (params.IS_RELEASE) {
                        if (BRANCH_NAME != INTEGRATION_BRANCH) {
                         // Invalid release branch
                            error("Release process must be executed from integration branch: " + INTEGRATION_BRANCH )
                         }
                        almMavenGitFlowReleaseStart(env.INTEGRATION_BRANCH, env.MASTER_BRANCH)
                        // sets the build name
                        currentBuild.displayName = "${pomMap['POM_ARTIFACTID']}:${pomMap['POM_VERSION']}-${env.BUILD_ID} (Release Build)"
                    } else {
                        // sets the build name
                        currentBuild.displayName = "${pomMap['POM_ARTIFACTID']}:${pomMap['POM_VERSION']}-${env.BUILD_ID}"
                    }                   
                }
                // executes mvn clean compile goal
                almMaven(goal: 'clean verify')
            }
            post {
                always {
                    // archives test results
                    junit testResults: 'target/**/TEST-*.xml', allowEmptyResults: true
                }
            }
        }
        stage("SonarQube") {
            when {
                not { expression { return params.DEPLOY_ONLY } }
            }
            steps {
                // only master and develop upload sonar analysis
                almSonar(analisysMode: (env.BRANCH_NAME == env.MASTER_BRANCH || env.BRANCH_NAME == env.INTEGRATION_BRANCH) ? '' : 'preview')
            }
        }
        stage("Nexus") {
            // only main branches uploads artifacts to nexus
            // this stage if omited if release because the artifact will be uploaded by
            // master branch
            // uploads the artifacts to nexus and
            // saves project's root uploaded artifact url
            when {
                allOf {
                    expression { return !params.IS_RELEASE }
                    anyOf {
                        environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                        environment name: 'BRANCH_NAME', value: env.INTEGRATION_BRANCH
                    }
                    not { expression { return params.DEPLOY_ONLY } }
                }
            }
            steps {
                script {
                    // uploads generated artifacts to Nexus
                    // and saves the uploaded artifact URL
                    // (in case of multi-module projects returned URL is project parent pom
                    // so use almMavenResolver instead)
                    artifactUrl = almMavenUpload()
                }
            }
        }
        stage('Git Merge (Release)') {
            when {
                allOf {
                    // When release is launched commits changes
                    expression { return params.IS_RELEASE }
                    not { expression { return params.DEPLOY_ONLY } }
                }
            }
            steps {
                 // merges branches and pushes changes and the tag
                 // see https://gitlab.alm.gsnetcloud.corp/serenity-alm/pipeline-library/wikis/vars#almmavengitflowreleaseend
                 almMavenGitFlowReleaseEnd(INTEGRATION_BRANCH, MASTER_BRANCH)            
            }
        }
        stage("Deploy DEV") {
        // deploys the artifact in OpenShift
        // only Integration branch and Master branch builds are deployed
            when {
                allOf {
                    expression { return !params.IS_RELEASE }
                    anyOf {
                        environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                        environment name: 'BRANCH_NAME', value: env.INTEGRATION_BRANCH
                        allOf {
                            expression { return params.DEPLOY_ONLY }
                            expression { params.DEPLOY_ENVIRONMENT == "DEV" }
                        }
                             
                    }           
                }
            }
            agent {
                // executes this stage in openshift agent
                label 'ose3-deploy'
            }
            steps {
                script {                 
                    almOpenshiftDeploy(
                        ose3Region: "${ose3Region}",
                        ose3Project: "${ose3Project}-dev",
                        ose3Application: "${pomMap['POM_ARTIFACTID']}",
                        ose3Template: 'javase',
                        ose3TokenCredentialId: "${ose3TokenID}-DEV",
                        ose3TemplateParams: [ARTIFACT_URL: artifactUrl, JAVA_OPTS_EXT: "-Dspring.profiles.active=dev"]
                    )
                }
            }
        }
        stage("Deployment approve to PRE"){
            when {
                expression { return !params.IS_RELEASE }
                anyOf {
                    environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                    allOf {
                        expression { return params.DEPLOY_ONLY }
                        expression { params.DEPLOY_ENVIRONMENT == "PRE-PRO" }
                    }
                }           
            }
            agent none
            steps {
                script {
                    echo "Approvers to PRE: $approversPre"
                    credId = almInputCredentialsList(
                            id: 'promotePre',
                            message: 'Select a deployment credential for PRE environment',
                            submitter: approversPre,
                            credentialType: 'org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl')
                    echo "Using credentials: $credId"
                }
            }
        }
        stage("Deploy PRE") {
        // deploys the artifact in OpenShift
        // only Master branch builds are deployed to PRE
            when {
                expression { return !params.IS_RELEASE }
                anyOf {
                    environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                    allOf {
                        expression { return params.DEPLOY_ONLY }
                        expression { params.DEPLOY_ENVIRONMENT == "PRE-PRO" }
                    }
                }           
            }
            agent {
                // executes this stage in openshift agent
                label 'ose3-deploy'
            }
            steps {
                script {              
                    almOpenshiftDeploy(
                        ose3Region: "${ose3Region}",
                        ose3Project: "${ose3Project}-pre",
                        ose3Application: "${pomMap['POM_ARTIFACTID']}",
                        ose3Template: 'javase',
                        ose3TokenCredentialId: credId,
                        ose3TemplateParams: [ARTIFACT_URL: artifactUrl, JAVA_OPTS_EXT: "-Dspring.profiles.active=pre"]
                    )
                }
            }
        }
        stage("Deployment approve to PRO"){
            when {
                expression { return !params.IS_RELEASE }
                anyOf {
                    environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                    allOf {
                        expression { return params.DEPLOY_ONLY }
                        expression { params.DEPLOY_ENVIRONMENT == "PRE-PRO" }
                    }
                }           
            }
            agent none
            steps {
                script {
                    echo "Approvers to PRO: $approversPro"
                    credId = almInputCredentialsList(
                            id: 'promotePro',
                            message: 'Select a deployment credential for PRO environment',
                            submitter: approversPro,
                            credentialType: 'org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl')
                    echo "Using credentials: $credId"
                }
            }
        }
        stage("Deploy PRO") {
        // deploys the artifact in OpenShift
        // only Master branch builds are deployed to PRO
            when {
                expression { return !params.IS_RELEASE }
                anyOf {
                    environment name: 'BRANCH_NAME', value: env.MASTER_BRANCH
                    allOf {
                        expression { return params.DEPLOY_ONLY }
                        expression { params.DEPLOY_ENVIRONMENT == "PRE-PRO" }
                    }
                }           
            }
            agent {
                // executes this stage in openshift agent
                label 'ose3-deploy'
            }
            steps {
                script {              
                    almOpenshiftDeploy(
                        ose3Region: "${ose3Region}",
                        ose3Project: "${ose3Project}-pro",
                        ose3Application: "${pomMap['POM_ARTIFACTID']}",
                        ose3Template: 'javase',
                        ose3TokenCredentialId: credId,
                        ose3TemplateParams: [ARTIFACT_URL: artifactUrl, JAVA_OPTS_EXT: "-Dspring.profiles.active=pro"]
                    )
                }
            }
        }
    }
    post {
        always {
            // updates the commit status according to build result
            almGitUpdateCommitStatus(name: 'build')
        }
        changed {
            //send notification
            almMail()
        } 
    }
}