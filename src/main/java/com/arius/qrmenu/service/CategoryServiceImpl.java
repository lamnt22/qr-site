package com.arius.qrmenu.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Category;
import com.arius.qrmenu.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category update(Long id, Category category) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        Category exitstingCategory = categoryRepository.findById(id)
            .get();

        exitstingCategory.setId(category.getId());
        exitstingCategory.setName(category.getName());
        exitstingCategory.setStatus(category.getStatus());
        exitstingCategory.setUpdatedAt(dateFormat.format(date));

        return categoryRepository.save(exitstingCategory);
    }

    @Override
    public Page<Category> findByKeyword(String keyword, String sortBy, int page, int size) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        Page<Category> categories = categoryRepository.findByKeyword(keyword, pageable);
        return categories;
    }

    @Override
    public List<Category> findAllActiveCategory() {
        return categoryRepository.findAllActiveCategory();
    }

    @Override
    public Page<Category> findAllCategory(String sortBy, int page, int size) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        return categoryRepository.findAllCategory(pageable);
    }

    @Override
    public void deleteCategory(Long id) {
        Category exitstingCategory = categoryRepository.findById(id)
            .get();
        exitstingCategory.setStatus(0);
        categoryRepository.save(exitstingCategory);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
