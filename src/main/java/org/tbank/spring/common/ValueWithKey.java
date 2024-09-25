package org.tbank.spring.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ValueWithKey<TKey> {
    @JsonIgnore
    TKey getKey();
}
