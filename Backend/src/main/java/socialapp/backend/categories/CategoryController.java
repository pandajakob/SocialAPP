package socialapp.backend.categories;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import socialapp.backend.categories.exceptions.ErrorResponse;
import socialapp.backend.categories.exceptions.NoSuchCategoryExistsException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/name/{name}")
    Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("/{id}")
    Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/main/")
    List<Category> getAllMainCategories() {
        return categoryService.getAllMainCategories();
    }

    @GetMapping("/sub/{name}")
    List<Category> getAllSubCategories(@PathVariable String name) {
        return categoryService.getAllSubCategoriesByName(name);
    }

    // Adding exception handlers for NoSuchCustomerExistsException
    // and NoSuchElementException.
    @ExceptionHandler(value = NoSuchCategoryExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchCustomerExistsException(NoSuchCategoryExistsException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
