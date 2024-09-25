package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tbank.spring.common.DataSource;

@Component
public class CategoriesDataLoader {

    @Autowired
    private DataSource<Integer, Category> dataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Category[] categories;
        try {
            System.out.println("Начинаю грузить Categories.");
            categories = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/place-categories/", Category[].class).getBody();
        } catch (RestClientException e) {
            System.out.println("Не смогли загрузить Categories.");
            return;
        }
        if (categories == null) {
            System.out.println("Categories нет.");
            return;
        }
        System.out.println("Сохраняю Categories в базу данных.");
        for (Category category : categories) {
            dataSource.put(category);
        }
        System.out.println("Сохранил Categories в базу данных.");
    }
}
