package com.dan.shoe.perfume.services.impls;

import com.dan.shoe.perfume.models.Category;
import com.dan.shoe.perfume.repositories.CategoryRepository;
import com.dan.shoe.perfume.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return categoryRepository.findById(id).map(
                category1 -> {
                    category1.setName(category.getName());
                    category1.setStatus(category.isStatus());
                    return categoryRepository.save(category1);
                }
        ).orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findByStatusTrue();
    }

    @Override
    public Page<Category> getAllCategories(String name, String status, Pageable pageable) {
        if (status.isEmpty()) {
            return categoryRepository.findAllByNameContaining(name, pageable);
        }
        boolean active = status.equalsIgnoreCase("true");
        return categoryRepository.findAllByNameContainingAndStatus(name, active, pageable);
    }
}
