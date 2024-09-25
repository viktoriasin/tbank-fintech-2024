package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tbank.spring.common.DataSource;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/places/categories")
public class CategoriesController {

    @Autowired
    private DataSource<Integer, Category> dataSource;

    @GetMapping("")
    public List<Category> getCategories() {
        return dataSource.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Integer id) {
        return dataSource.get(id);
    }

    @PostMapping("")
    public void postCategory(
            @RequestParam Integer id,
            @RequestParam String slug,
            @RequestParam String name
    ) {
        dataSource.put(new Category(id, slug, name));
    }

    @PutMapping("/{id}")
    public void putCategory(
            @PathVariable(value = "id") Integer id,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String name
    ) {
        dataSource.update(id, category -> {
            if (slug != null) category.setSlug(slug);
            if (name != null) category.setName(name);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer id) {
        dataSource.delete(id);
    }
}
