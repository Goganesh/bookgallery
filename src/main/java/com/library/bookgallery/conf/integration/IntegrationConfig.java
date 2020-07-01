package com.library.bookgallery.conf.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;

@Configuration
@IntegrationComponentScan
public class IntegrationConfig {

    @Bean
    public QueueChannel authorInputChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel authorOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow authorFlow() {
        return IntegrationFlows.from("authorInputChannel")
                .split()
                .publishSubscribeChannel(subscription ->
                        subscription.subscribe(subflow -> subflow
                        .<Message, String>route(Message.class, m-> m.getHeaders().get("customer", String.class), r -> r
                        .subFlowMapping("author_save", sf -> sf
                                .handle("authorServiceImpl","save")
                                .channel("authorOutputChannel"))
                        .subFlowMapping("author_delete", sf -> sf
                                .handle("authorServiceImpl","deleteById")
                                .channel("authorOutputChannel")))))
                .get();
    }
}
