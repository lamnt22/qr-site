package com.arius.qrmenu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.arius.qrmenu.common.Constants;
import com.arius.qrmenu.form.CategoryForm;
import com.arius.qrmenu.model.Category;
import com.arius.qrmenu.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping ("")
    public ResponseEntity<Page<Category>> getListCategory(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<Category>>(categoryService.findAllCategory(sortBy, page, size), HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody final CategoryForm categoryForm) {

        List<String> errors = new ArrayList<>();
        Optional<Category> existingCategory = categoryService.findByName(categoryForm.getName());

        if (existingCategory.isPresent()) {
            errors.add(Constants.CategoryValidation.CATEGORY_NAME_EXIST);
            return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
        }

        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryForm, category);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            category.setCreatedAt(dateFormat.format(currentDate));
            categoryService.save(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody final CategoryForm categoryForm,
        @PathVariable ("id") final Long id) {

        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryForm, category);
            if (id != null) {
                categoryService.update(id, category);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable ("id") final Long id) {

        if (id != null) {
            return new ResponseEntity<Category>(categoryService.findById(id)
                .get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable ("id") final Long id) {

        try {
            if (id != null) {
                categoryService.deleteCategory(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/search")
    public ResponseEntity<Page<Category>> search(@RequestParam ("keyword") final String keyword,
        @RequestParam ("page") final int page, @RequestParam ("size") final int size,
        @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<Category>>(categoryService.findByKeyword(keyword, sortBy, page, size),
            HttpStatus.OK);
    }

    @GetMapping ("/getCategorySelected")
    public ResponseEntity<List<Category>> getCategorySelected() {

        return new ResponseEntity<List<Category>>(categoryService.findAllActiveCategory(), HttpStatus.OK);
    }
}
