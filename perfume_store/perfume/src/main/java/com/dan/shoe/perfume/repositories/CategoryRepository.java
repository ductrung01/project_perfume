package com.dan.shoe.perfume.repositories;

import com.dan.shoe.perfume.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
    Page<Category> findAllByNameContaining(String name, Pageable pageable);
    Page<Category> findAllByNameContainingAndStatus(String name, boolean status, Pageable pageable);
    List<Category> findByStatusTrue();
}
