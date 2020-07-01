package com.library.bookgallery.conf.integration;

import com.library.bookgallery.domain.Author;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface AuthorGate {

    @Gateway(requestChannel = "authorInputChannel", replyChannel = "authorOutputChannel")
    void process(Message author);
}
