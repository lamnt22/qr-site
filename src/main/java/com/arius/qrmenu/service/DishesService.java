package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Dishes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface DishesService {

    void deleteById(Long id);

    long count();

    Optional<Dishes> findById(Long id);

    Page<Dishes> findAll(String sortBy, int page, int size);

    List<Dishes> findAll(Sort sort);

    Dishes save(Dishes entity);

    Dishes getDishesByName(String name);

    Dishes updateDishes(Long id, Dishes dishes);

    Page<Dishes> findByKeyword(String keyword, String sortBy, int page, int size);

    Page<Dishes> findAllDishes(Pageable pageable);

    void updateDeleteFlag(Long id);

    List<Dishes> findAllActiveDishes();

}
