package com.library.bookgallery.conf;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.web.reactive.function.server.*;
import java.net.URI;

@Configuration
@AllArgsConstructor
public class AppConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public RouterFunction<ServerResponse> mainPageRouter(
            @Value("classpath:/templates/books.html") Resource html){
        return RouterFunctions.route(RequestPredicates.GET("/"), request -> ServerResponse
                .temporaryRedirect(URI.create("/books")).build());
    }

    @Bean
    public RouterFunction<ServerResponse> resourceRouter() {
        return RouterFunctions.resources("/static/**", new ClassPathResource("static/"));

    }
    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "library";
    }
}
