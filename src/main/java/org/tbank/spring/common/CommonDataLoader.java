package org.tbank.spring.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommonDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDataLoader.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public <TKey, TValue extends ValueWithKey<? extends TKey>> void loadData(Class<TValue> cls, String url, DataSource<TKey, TValue> dataSource) {
        try {
            LOGGER.info("Start loading {}", cls.getSimpleName());
            TValue[] values = (TValue[]) restTemplate.getForEntity(url, cls.arrayType()).getBody();
            if (values != null) {
                LOGGER.info("Done loading {}: {} item(s)", cls.getSimpleName(), values.length);
                for (TValue value : values) {
                    dataSource.put(value);
                }
                LOGGER.info("Save {} to DB", cls.getSimpleName());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load {}", cls.getSimpleName(), e);
        }
    }
}
