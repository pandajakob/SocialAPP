package socialapp.api.categories;

import org.springframework.stereotype.Service;
import socialapp.api.categories.exceptions.NoSuchCategoryExistsException;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        for (Category c : categoryRepository.findAll()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new NoSuchCategoryExistsException("Category with name " + name + " does not exist");
    }

    public List<Category> getAllMainCategories() {
        return categoryRepository.findAll().stream().filter(c-> c.getParentCategoryId() == null).toList();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchCategoryExistsException("Category with id "+id + " does not exist"));
    }

    public Category getUltimateParentCategory(Long id) {
        Category current = getCategoryById(id);
        Set<Long> visited = new HashSet<>();
        visited.add(current.getId());
        while (current.getParentCategoryId() != null) {
            Long parentId = current.getParentCategoryId();
            if (!visited.add(parentId)) {
                throw new IllegalStateException("Circular category hierarchy detected at id " + parentId);
            }
            current = getCategoryById(parentId);
        }
        return current;
    }

    public List<Category> getAllSubCategoriesByName(String name) {
        Category category = getCategoryByName(name);
        return categoryRepository.findAll().stream().filter(c->{
            Long parentId = c.getParentCategoryId();
            return parentId != null && parentId == category.getId();
        }).toList();
    }
}
