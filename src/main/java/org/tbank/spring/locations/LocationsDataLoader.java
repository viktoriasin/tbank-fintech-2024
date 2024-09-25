package org.tbank.spring.locations;

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
public class LocationsDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsDataLoader.class);

    @Autowired
    private DataSource<Integer, Location> dataSource;

    private final RestTemplate restTemplate = new RestTemplate();

    @Timed
    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Location[] locations;
        try {
            LOGGER.info("Начинаю грузить Locations.");
            locations = restTemplate.getForEntity("https://kudago.com/public-api/v1.4/locations/", Location[].class).getBody();
        } catch (RestClientException e) {
            LOGGER.info("Не смогли загрузить Locations.");
            return;
        }
        if (locations == null) {
            LOGGER.info("Locations нет.");
            return;
        }
        LOGGER.info("Сохраняю Locations ({}) в базу данных.", locations.length);
        for (Location location : locations) {
            dataSource.put(location);
        }
        LOGGER.info("Сохранил Locations в базу данных.");
    }
}
