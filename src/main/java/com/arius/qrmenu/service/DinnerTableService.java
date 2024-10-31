package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.DinnerTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public interface DinnerTableService {

    void deleteById(int id);

    long count();

    Optional<DinnerTable> findById(int id);

    Page<DinnerTable> findAll(Pageable pageable);

    List<DinnerTable> findAll(Sort sort);

    ResponseEntity<?> save(DinnerTable dinnerTable);

    Optional<DinnerTable> findDinnerTableByName(String name);

    ResponseEntity<?> updateDinnerTable(int id, DinnerTable dinnerTable);

    Page<DinnerTable> search(int page, int size, String sortValue, String name, String status);

	List<DinnerTable> findAllDinnerTableActive();
	
	

}
