package com.library.bookgallery.batch.job;

import com.library.bookgallery.batch.model.AuthorMongo;
import com.library.bookgallery.batch.model.BookMongo;
import com.library.bookgallery.batch.model.GenreMongo;
import com.library.bookgallery.batch.processor.AuthorItemProcessor;
import com.library.bookgallery.batch.processor.BookItemProcessor;
import com.library.bookgallery.batch.processor.GenreItemProcessor;
import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.domain.Genre;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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
    private final DataSource dataSource;
    private final EntityManagerFactory entityManagerFactory;

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
        return new BookItemProcessor();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Author> jdbcAuthorWriter() {
        return new JdbcBatchItemWriterBuilder<Author>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO authors (name) VALUES (:name)")
                .dataSource(dataSource)
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Genre> jdbcGenreWriter() {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO genres (name) VALUES (:name)")
                .dataSource(dataSource)
                .build();
    }

    public JpaItemWriter<Book> jpaBookWriter(){
        return new JpaItemWriterBuilder<Book>()
                .entityManagerFactory(entityManagerFactory)
                .build();
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
                .writer(jdbcAuthorWriter())
                .listener(new ItemReadListener() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Object o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
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
                .writer(jdbcGenreWriter())
                .listener(new ItemReadListener() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Object o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
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
                .listener(new ItemReadListener() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Object o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
}
