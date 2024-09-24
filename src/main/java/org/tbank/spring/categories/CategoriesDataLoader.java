package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CategoriesDataLoader {

    @Autowired
    private CategoriesDataSource categoriesDataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Category[] categories;
        try {
            System.out.println("Начинаю грузить данные.");
            categories = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/place-categories/", Category[].class).getBody();
        } catch (RestClientException e) {
            System.out.println("Не смогли загрузить данные.");
            return;
        }
        if (categories == null) {
            System.out.println("Данных нет.");
            return;
        }
        System.out.println("Сохраняю записи в базу данных.");
        for (Category category : categories) {
            categoriesDataSource.addCategory(category);
        }
        System.out.println("Сохранил записи в базу данных.");
    }

}
