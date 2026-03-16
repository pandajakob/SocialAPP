package socialapp.backend.categories;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private Map<Long, Category> categoryMap = new HashMap<>();

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.loadCategories();
    }

    private void loadCategories() {
        categoryRepository.findAll().forEach(c -> categoryMap.put(c.getId(),c));
    }

    public List<Category> getAllCategories() {
        return categoryMap.values().stream().toList();
    }

    public Category getCategoryByName(String name) {
        for (Category c : categoryMap.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new InputMismatchException("Category with name " + name + " does not exist");
    }

    List<Category> getAllMainCategories() {
        return categoryMap.values().stream().filter(c-> c.getParentCategoryId() == null).toList();
    }

    List<Category> getAllSubCategoriesByName(String name) {
        return categoryMap.values().stream().filter(c->{
            Long parentId = c.getParentCategoryId();
            return categoryMap.get(parentId).getName().equalsIgnoreCase(name);
        }).toList();
    }
}
