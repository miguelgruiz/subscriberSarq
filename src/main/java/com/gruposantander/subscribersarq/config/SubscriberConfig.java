package com.gruposantander.subscribersarq.config;

import com.gruposantander.subscribersarq.channels.SubscriberChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(SubscriberChannel.class)
public class SubscriberConfig {

}
