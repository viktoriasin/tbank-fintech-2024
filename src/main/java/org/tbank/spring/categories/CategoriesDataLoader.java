package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CategoriesDataLoader {

    @Autowired
    private CategoriesDataSource categoriesDataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    public void loadData() {
        Category[] categories;
        try {
            categories = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/place-categories/", Category[].class).getBody();
        } catch (RestClientException e) {
            System.out.println("Не смогли загрузить данные.");
            return;
        }
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            categoriesDataSource.addCategory(category);
        }
    }

}
