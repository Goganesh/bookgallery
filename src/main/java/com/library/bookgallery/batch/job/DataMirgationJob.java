package com.library.bookgallery.batch.job;

import com.library.bookgallery.batch.model.AuthorMongo;
import com.library.bookgallery.batch.model.BookMongo;
import com.library.bookgallery.batch.model.GenreMongo;
import com.library.bookgallery.batch.processor.AuthorItemProcessor;
import com.library.bookgallery.batch.processor.BookItemProcessor;
import com.library.bookgallery.batch.processor.GenreItemProcessor;
import com.library.bookgallery.batch.writer.AuthorJpaIterWriter;
import com.library.bookgallery.batch.writer.BookJpaItemWriter;
import com.library.bookgallery.batch.writer.GenreJpaItemWriter;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import com.library.bookgallery.service.AuthorService;
import com.library.bookgallery.service.BookService;
import com.library.bookgallery.service.GenreService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DataMirgationJob {

    private static final int CHUNK_SIZE = 5;
    public static final String IMPORT_DATA_JOB_NAME = "importMongoDataJob";

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MongoTemplate mongoTemplate;
    private final EntityManagerFactory entityManagerFactory;

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    @StepScope
    @Bean
    public MongoItemReader<AuthorMongo> mongoAuthorReader() {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("mongoAuthorReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<GenreMongo> mongoGenreReader() {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("mongoGenreReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<BookMongo> mongoBookReader() {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("mongoBookReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public AuthorItemProcessor authorItemProcessor() {
        return new AuthorItemProcessor();
    }

    @StepScope
    @Bean
    public GenreItemProcessor genreItemProcessor() {
        return new GenreItemProcessor();
    }

    @StepScope
    @Bean
    public BookItemProcessor bookItemProcessor() {
        return new BookItemProcessor(authorService, genreService);
    }

    @StepScope
    @Bean
    public ItemWriter<Author> jpaAuthorWriter() {
        AuthorJpaIterWriter authorJpaIterWriter = new AuthorJpaIterWriter(authorService);
        authorJpaIterWriter.setEntityManagerFactory(entityManagerFactory);
        return authorJpaIterWriter;
    }

    @StepScope
    @Bean
    public ItemWriter<Genre> jpaGenreWriter() {
        GenreJpaItemWriter genreJpaItemWriter = new GenreJpaItemWriter(genreService);
        genreJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return genreJpaItemWriter;
    }

    public ItemWriter<Book> jpaBookWriter(){
        BookJpaItemWriter bookJpaItemWriter = new BookJpaItemWriter(bookService);
        bookJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return bookJpaItemWriter;
    }

    @Bean
    public Job importMongoDataJob() {
        return jobBuilderFactory.get(IMPORT_DATA_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(importAuthors())
                .next(importGenres())
                .next(importBooks())
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step importAuthors() {
        return stepBuilderFactory
                .get("importAuthors")
                .<AuthorMongo, Author> chunk(CHUNK_SIZE)
                .reader(mongoAuthorReader())
                .processor(authorItemProcessor())
                .writer(jpaAuthorWriter())
                .listener(new ItemReadListener<>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(AuthorMongo o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener<AuthorMongo, Author>() {
                    public void beforeProcess(AuthorMongo o) {logger.info("Начало обработки");}
                    public void afterProcess(AuthorMongo o, Author o2) {logger.info("Конец обработки");}
                    public void onProcessError(AuthorMongo o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener<Author>() {
                    public void beforeWrite(List<? extends Author> list) { logger.info("Начало записи"); }
                    public void afterWrite(List<? extends Author> list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List<? extends Author> list) { logger.info("Ошибка записи"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

    @Bean
    public Step importGenres() {
        return stepBuilderFactory
                .get("importGenres")
                .<GenreMongo, Genre> chunk(CHUNK_SIZE)
                .reader(mongoGenreReader())
                .processor(genreItemProcessor())
                .writer(jpaGenreWriter())
                .listener(new ItemReadListener<>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(GenreMongo o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener<GenreMongo, Genre>() {
                    public void beforeProcess(GenreMongo o) {logger.info("Начало обработки");}
                    public void afterProcess(GenreMongo o, Genre o2) {logger.info("Конец обработки");}
                    public void onProcessError(GenreMongo o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener<Genre>() {
                    public void beforeWrite(List<? extends Genre> list) { logger.info("Начало записи"); }
                    public void afterWrite(List<? extends Genre> list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List<? extends Genre> list) { logger.info("Ошибка записи"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

    @Bean
    public Step importBooks() {
        return stepBuilderFactory
                .get("importBooks")
                .<BookMongo, Book> chunk(CHUNK_SIZE)
                .reader(mongoBookReader())
                .processor(bookItemProcessor())
                .writer(jpaBookWriter())
                .listener(new ItemReadListener<>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(BookMongo o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener<BookMongo, Book>() {
                    public void beforeProcess(BookMongo o) {logger.info("Начало обработки");}
                    public void afterProcess(BookMongo o, Book o2) {logger.info("Конец обработки");}
                    public void onProcessError(BookMongo o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener<Book>() {
                    public void beforeWrite(List<? extends Book> list) { logger.info("Начало записи"); }
                    public void afterWrite(List<? extends Book> list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List<? extends Book> list) { logger.info("Ошибка записи"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
}
