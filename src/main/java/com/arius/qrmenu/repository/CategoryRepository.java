package com.arius.qrmenu.repository;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query ("SELECT c FROM Category c WHERE c.name LIKE %:keyword% AND c.status = 1")
    Page<Category> findByKeyword(String keyword, Pageable pageable);

    @Query ("SELECT c FROM Category c WHERE c.status = 1")
    Page<Category> findAllCategory(Pageable pageable);

    @Query ("SELECT c FROM Category c WHERE c.status = 1")
    List<Category> findAllActiveCategory();
}
