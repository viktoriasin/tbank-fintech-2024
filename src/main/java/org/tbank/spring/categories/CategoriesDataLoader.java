package org.tbank.spring.categories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tbank.spring.aop.Timed;
import org.tbank.spring.common.DataSource;

@Component
public class CategoriesDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriesDataLoader.class);

    @Autowired
    private DataSource<Integer, Category> dataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    @Timed
    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Category[] categories;
        try {
            LOGGER.info("Начинаю грузить Categories.");
            categories = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/place-categories/", Category[].class).getBody();
        } catch (RestClientException e) {
            LOGGER.info("Не смогли загрузить Categories.");
            return;
        }
        if (categories == null) {
            LOGGER.info("Categories нет.");
            return;
        }
        LOGGER.info("Сохраняю Categories ({}) в базу данных.", categories.length);
        for (Category category : categories) {
            dataSource.put(category);
        }
        LOGGER.info("Сохранил Categories в базу данных.");
    }
}
