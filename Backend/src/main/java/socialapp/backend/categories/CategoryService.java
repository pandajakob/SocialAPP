package socialapp.backend.categories;

import socialapp.backend.categories.exceptions.NoSuchCategoryExistsException;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    public List<Category> getAllCategories();

    public Category getCategoryByName(String name);

    List<Category> getAllMainCategories();

    Category getCategoryById(Long id);

    List<Category> getAllSubCategoriesByName(String name);


}
