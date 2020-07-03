package com.library.bookgallery.conf.integration;

import com.library.bookgallery.domain.Author;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

@MessagingGateway
public interface AuthorGate {

    @Gateway(requestChannel = "authorSaveInputChannel", replyChannel = "authorSaveOutputChannel")
    Author saveAuthor(Author author);

    @Gateway(requestChannel = "authorDeleteInputChannel", replyChannel = "authorDeleteOutputChannel")
    boolean deleteAuthorById(long id);

    @Gateway(requestChannel = "authorFindInputChannel", replyChannel = "authorFindOutputChannel")
    Author findAuthorById(long id);

    @Gateway(requestChannel = "authorFindAllInputChannel", replyChannel = "authorFindAllOutputChannel")
    @Payload("new java.util.Date()")
    List<Author> findAllAuthor();

    //@Gateway(requestChannel = "authorInputChannel", replyChannel = "authorOutputChannel")
    //void process(Message<?> author);


}
