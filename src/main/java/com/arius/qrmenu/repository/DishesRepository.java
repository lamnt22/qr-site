package com.arius.qrmenu.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arius.qrmenu.model.Dishes;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long>{

	@Query("SELECT d FROM Dishes d WHERE d.deleteFlag = 0 AND d.name LIKE %:keyword%")
	Page<Dishes> findByKeyword(String keyword, Pageable pageable);
	
	@Query("SELECT d FROM Dishes d WHERE d.deleteFlag = 0")
	Page<Dishes> findAllDishes(Pageable pageable);
	
	@Query("SELECT d FROM Dishes d WHERE d.deleteFlag = 0 AND d.name = ?1")
	Dishes getDishesByName(String name);
	
	@Query("SELECT d FROM Dishes d WHERE d.deleteFlag = 0")
	List<Dishes> findAllActiveDishes();
}
