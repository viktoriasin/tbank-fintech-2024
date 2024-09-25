package org.tbank.spring.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tbank.spring.common.DataSource;

@Component
public class LocationsDataLoader {

    @Autowired
    private DataSource<Integer, Location> dataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Location[] locations;
        try {
            System.out.println("Начинаю грузить Locations.");
            locations = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/locations/", Location[].class).getBody();
        } catch (RestClientException e) {
            System.out.println("Не смогли загрузить Locations.");
            return;
        }
        if (locations == null) {
            System.out.println("Locations нет.");
            return;
        }
        System.out.println("Сохраняю Locations в базу данных.");
        for (Location location : locations) {
            dataSource.put(location);
        }
        System.out.println("Сохранил Locations в базу данных.");
    }
}
