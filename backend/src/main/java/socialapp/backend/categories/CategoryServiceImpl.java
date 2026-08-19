package socialapp.backend.categories;

import org.springframework.stereotype.Service;
import socialapp.backend.categories.exceptions.NoSuchCategoryExistsException;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private Map<Long, Category> categoryMap = new HashMap<>();

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
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
        throw new NoSuchCategoryExistsException("Category with name " + name + " does not exist");
    }

    public List<Category> getAllMainCategories() {
        return categoryMap.values().stream().filter(c-> c.getParentCategoryId() == null).toList();
    }

    public Category getCategoryById(Long id) {
        Category cat = categoryMap.get(id);
        if (cat == null) {
            throw new NoSuchCategoryExistsException("Category with id "+id + " does not exist");
        }
        return cat;
    }

    public List<Category> getAllSubCategoriesByName(String name) {
        Category category = getCategoryByName(name);
        return categoryMap.values().stream().filter(c->{
            Long parentId = c.getParentCategoryId();
            return parentId != null && parentId == category.getId();
        }).toList();
    }
}
