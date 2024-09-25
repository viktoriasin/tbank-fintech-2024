package org.tbank.spring.locations;

import lombok.Data;
import lombok.NonNull;
import org.tbank.spring.common.ValueWithKey;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Location implements ValueWithKey<Integer> {

    private static AtomicInteger counter = new AtomicInteger(0);

    @NonNull
    private final Integer id = counter.getAndIncrement();

    @NonNull
    private String slug;

    @NonNull
    private String name;

    @Override
    public Integer getKey() {
        return id;
    }
}
