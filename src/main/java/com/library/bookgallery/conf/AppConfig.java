package com.library.bookgallery.conf;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

@Configuration
@PropertySource("classpath:application.yml")
@AllArgsConstructor
public class AppConfig {

    private final Environment env;

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename(env.getProperty("locale.localeClasspath"));
        ms.setDefaultEncoding(env.getProperty("locale.defaultEncoding"));
        ms.setDefaultLocale(new Locale(
                env.getProperty("locale.defaultLocaleLanguage"),
                env.getProperty("locale.defaultLocaleCountry")));
        return ms;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String driverClassName = env.getProperty("driver-class-name");
        String url = env.getProperty("url");
        String username = env.getProperty("user");
        String password = env.getProperty("password");
        logger.info("Driver class name - " + driverClassName + ", url - " + url + ", username - " + username + ", password - " + password);

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
