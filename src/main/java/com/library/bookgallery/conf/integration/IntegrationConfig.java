package com.library.bookgallery.conf.integration;

import com.library.bookgallery.service.AuthorServiceImpl;
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

    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public QueueChannel authorDeleteInputChannel(){ return MessageChannels.queue(10).get(); }

    @Bean
    public PublishSubscribeChannel authorDeleteOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorDeleteFlow() {
        return IntegrationFlows.from("authorDeleteInputChannel")
                .handle("authorServiceImpl","deleteById")
                .channel("authorDeleteOutputChannel")
                .get();
    }

    @Bean
    public QueueChannel authorSaveInputChannel(){ return MessageChannels.queue(10).get(); }

    @Bean
    public PublishSubscribeChannel authorSaveOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorSaveFlow() {
        return IntegrationFlows.from("authorSaveInputChannel")
                .handle("authorServiceImpl","save")
                .channel("authorSaveOutputChannel")
                .get();
    }

    @Bean
    public QueueChannel authorFindInputChannel(){ return MessageChannels.queue(10).get(); }

    @Bean
    public PublishSubscribeChannel authorFindOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorFindFlow() {
        return IntegrationFlows.from("authorFindInputChannel")
                .handle("authorServiceImpl","findById")
                .channel("authorFindOutputChannel")
                .get();
    }

    @Bean
    public QueueChannel authorFindAllInputChannel(){ return MessageChannels.queue(10).get(); }

    @Bean
    public PublishSubscribeChannel authorFindAllOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorFindAllFlow() {
        return IntegrationFlows.from("authorFindAllInputChannel")
                .handle("authorServiceImpl","findAll")
                .channel("authorFindAllOutputChannel")
                .get();
    }

    /*@Bean
    public QueueChannel authorInputChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel authorOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorFlow() {
        return IntegrationFlows.from("authorInputChannel")
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
    }*/
}
