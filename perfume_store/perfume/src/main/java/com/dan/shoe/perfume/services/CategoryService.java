package com.dan.shoe.perfume.services;


import com.dan.shoe.perfume.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
    Category getCategory(Long id);
    List<Category> getAllCategories();
    Page<Category> getAllCategories(String name, String status, Pageable pageable);
}
