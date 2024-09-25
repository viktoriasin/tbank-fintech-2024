package org.tbank.spring.categories;

import lombok.Data;
import lombok.NonNull;
import org.tbank.spring.common.ValueWithKey;

@Data
public class Category implements ValueWithKey<Integer> {

    @NonNull
    private final Integer id;

    @NonNull
    private String slug;

    @NonNull
    private String name;

    @Override
    public Integer getKey() {
        return id;
    }
}
