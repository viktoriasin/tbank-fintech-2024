package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tbank.spring.aop.Timed;
import org.tbank.spring.common.CommonDataLoader;
import org.tbank.spring.common.DataSource;

@Component
public class CategoriesDataLoader {

    private static final String URL = "https://kudago.com/public-api/v1.4/place-categories/";

    @Autowired
    private DataSource<Integer, Category> dataSource;

    @Autowired
    private CommonDataLoader commonDataLoader;

    @Timed
    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        commonDataLoader.loadData(Category.class, URL, dataSource);
    }
}
