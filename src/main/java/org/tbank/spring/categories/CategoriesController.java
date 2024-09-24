package org.tbank.spring.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/places/categories")
public class CategoriesController {

    @Autowired
    private CategoriesDataSource categoriesDataSource;

    @GetMapping("")
    public List<Category> getCategories() {
        return categoriesDataSource.getCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Integer id) {
        return categoriesDataSource.getCategory(id);
    }

    @PostMapping("")
    public void postCategory(
            @RequestParam Integer id,
            @RequestParam String slug,
            @RequestParam String name
    ) {
        categoriesDataSource.addCategory(new Category(id, slug, name));
    }

    @PutMapping("/{id}")
    public void putCategory(
            @PathVariable(value = "id") Integer id,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String name
    ) {
        categoriesDataSource.updateCategory(id, slug, name);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer id) {
        categoriesDataSource.deleteCategory(id);
    }
}
