package com.gruposantander.subscribersarq.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.gruposantander.subscribersarq.channels.SubscriberChannel;

@EnableBinding(SubscriberChannel.class)
public class SubscriberConfig {

}
