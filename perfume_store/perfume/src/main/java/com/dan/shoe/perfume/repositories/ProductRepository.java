package com.dan.shoe.perfume.repositories;

import com.dan.shoe.perfume.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingAndDeletedFalse(String name, Pageable pageable);
    Page<Product> findByNameContainingAndStatusAndDeletedFalse(String name, boolean status, Pageable pageable);
}
