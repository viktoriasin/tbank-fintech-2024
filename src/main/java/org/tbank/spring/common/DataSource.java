package org.tbank.spring.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class DataSource<TKey, TValue extends ValueWithKey<? extends TKey>> {

    private final Map<TKey, TValue> dataSource = new ConcurrentHashMap<>();

    public List<TValue> getAll() {
        return new ArrayList<>(dataSource.values());
    }

    public TValue get(TKey key) {
        return dataSource.get(key);
    }

    public void put(TValue value) {
        dataSource.put(value.getKey(), value);
    }

    public void update(TKey key, Consumer<TValue> transformer) {
        TValue value = get(key);
        transformer.accept(value);
        put(value);
    }

    public void delete(TKey key) {
        if (dataSource.remove(key) != null) {
            System.out.println("Данные по ключу " + key + " были удалены.");
        } else {
            System.out.println("Категории по ключу " + key + " в базе нет.");
        }
    }
}
