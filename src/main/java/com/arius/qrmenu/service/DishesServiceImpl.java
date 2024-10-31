package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Dishes;
import com.arius.qrmenu.repository.DishesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DishesServiceImpl implements DishesService {

    @Autowired
    private DishesRepository dishesRepository;

    @Override
    public Dishes save(Dishes entity) {
        return dishesRepository.save(entity);
    }

    @Override
    public List<Dishes> findAll(Sort sort) {
        return dishesRepository.findAll(sort);
    }

    @Override
    public Page<Dishes> findAll(String sortBy, int page, int size) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        return dishesRepository.findAllDishes(pageable);
    }

    @Override
    public Optional<Dishes> findById(Long id) {
        return dishesRepository.findById(id);
    }

    @Override
    public long count() {
        return dishesRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        dishesRepository.deleteById(id);
    }

    @Override
    public Dishes getDishesByName(String name) {
        return dishesRepository.getDishesByName(name);
    }

    @Override
    public Dishes updateDishes(Long id, Dishes dishes) {
        Dishes exitstingDishes = dishesRepository.findById(id)
            .get();
        exitstingDishes.setName(dishes.getName());
        exitstingDishes.setImage(dishes.getImage());
        exitstingDishes.setPrice(dishes.getPrice());
        exitstingDishes.setDescription(dishes.getDescription());
        exitstingDishes.setStatus(dishes.getStatus());
        exitstingDishes.setCategoryId(dishes.getCategoryId());

        Dishes updateDishes = dishesRepository.save(exitstingDishes);
        return updateDishes;
    }

    @Override
    public void updateDeleteFlag(Long id) {
        Dishes exitstingDishes = dishesRepository.findById(id)
            .get();
        exitstingDishes.setDeleteFlag(1);
        dishesRepository.save(exitstingDishes);
    }

    @Override
    public Page<Dishes> findByKeyword(String keyword, String sortBy, int page, int size) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        return dishesRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Dishes> findAllDishes(Pageable pageable) {
        return dishesRepository.findAllDishes(pageable);
    }

    @Override
    public List<Dishes> findAllActiveDishes() {
        return dishesRepository.findAllActiveDishes();
    }

}
