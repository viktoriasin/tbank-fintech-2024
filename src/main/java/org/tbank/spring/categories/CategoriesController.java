package org.tbank.spring.categories;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/places/categories")
public class CategoriesController {

    @GetMapping("")
    public String getCategories() {
        return "Hello world";
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable(value = "id") String id) {
        return id;
    }

    @PostMapping("")
    public String postCategory(
            @RequestParam String id,
            @RequestParam String slug,
            @RequestParam String name
    ) {
        return id + ", " + slug + ", " + name;
    }

    @PutMapping("/{id}")
    public String putCategory(
            @PathVariable(value = "id") String id,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String name
    ) {
        return id + ", " + slug + ", " + name;
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable(value = "id") String id) {
        return id;
    }

}
