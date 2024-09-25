package org.tbank.spring.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tbank.spring.aop.Timed;
import org.tbank.spring.common.CommonDataLoader;
import org.tbank.spring.common.DataSource;

@Component
public class LocationsDataLoader {

    private static final String URL = "https://kudago.com/public-api/v1.4/locations/";

    @Autowired
    private DataSource<Integer, Location> dataSource;

    @Autowired
    private CommonDataLoader commonDataLoader;

    @Timed
    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        commonDataLoader.loadData(Location.class, URL, dataSource);
    }
}
