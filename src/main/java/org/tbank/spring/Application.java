package org.tbank.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.tbank.spring.categories.Category;
import org.tbank.spring.common.DataSource;
import org.tbank.spring.locations.Location;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DataSource<Integer, Category> dataSourceCategories() {
        return new DataSource<>();
    }

    @Bean
    public DataSource<Integer, Location> dataSourceLocations() {
        return new DataSource<>();
    }
}
