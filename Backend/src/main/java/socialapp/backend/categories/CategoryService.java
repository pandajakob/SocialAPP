package socialapp.backend.categories;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryList categories = new CategoryList();

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryByName(String name) {
        return categories.getCategoryByName(name);
    }

    List<Category> getAllMainCategories() {
        return categories.getAllMainCategories();
    }

    List<Category> getAllSubCategories(String name) {
        return categories.getAllSubCategories(name);
    }
}
