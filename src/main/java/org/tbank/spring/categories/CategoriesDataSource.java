package org.tbank.spring.categories;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CategoriesDataSource {
    private final Map<Integer, Category> dataSource = new ConcurrentHashMap<>();

    public List<Category> getCategories() {
        return new ArrayList<>(dataSource.values());
    }

    public Category getCategory(int id) {
        return dataSource.get(id);
    }

    public void addCategory(int id, @NonNull String slug, @NonNull String name) {
        dataSource.put(id, new Category(id, slug, name));
    }

    public void updateCategory(int id, String slug, String name) {
        Category categoryToUpdate = getCategory(id);
        if (categoryToUpdate != null) {
            slug = (slug == null) ? categoryToUpdate.getSlug() : slug;
            name = (name == null) ? categoryToUpdate.getName() : name;
            dataSource.put(id, new Category(id, slug, name));
        } else {
            System.out.println("Категории по ключу " + id + " в базе нет.");
        }
    }

    public void deleteCategory(int id) {
        if (dataSource.remove(id) != null) {
            System.out.println("Данные по ключу " + id + " были удалены.");
        } else {
            System.out.println("Категории по ключу " + id + " в базе нет.");
        }
    }
}
