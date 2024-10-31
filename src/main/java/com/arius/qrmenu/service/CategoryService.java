package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Optional<Category> findById(Long id);

    Page<Category> findAll(Pageable pageable);

    Category save(Category entity);

    Category update(Long id, Category category);

    Page<Category> findAllCategory(String sortBy, int page, int size);

    Page<Category> findByKeyword(String keyword, String sortBy, int page, int size);

    void deleteCategory(Long id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    List<Category> findAllActiveCategory();

}
