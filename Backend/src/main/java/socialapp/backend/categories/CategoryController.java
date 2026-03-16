package socialapp.backend.categories;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{name}")
    Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("/main/")
    List<Category> getAllMainCategories() {
        return categoryService.getAllMainCategories();
    }

    @GetMapping("/sub/{name}")
    List<Category> getAllSubCategories(@PathVariable String name) {
        return categoryService.getAllSubCategoriesByName(name);
    }




}
