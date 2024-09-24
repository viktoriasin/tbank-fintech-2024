package org.tbank.spring.categories;

import lombok.Data;
import lombok.NonNull;

@Data
public class Category {
    private final int id;
    @NonNull
    private final String slug;
    @NonNull
    private final String name;
}
