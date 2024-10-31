package com.arius.qrmenu.repository;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.DinnerTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {

    Optional<DinnerTable> findByName(String name);

    @Query ("SELECT d FROM DinnerTable d WHERE d.deleteFlag = 0 AND d.name LIKE %:name%  AND d.status LIKE :status%")
    Page<DinnerTable> findByNameAndStatus(String name, String status, Pageable pageable);

    @Query ("SELECT d FROM DinnerTable d WHERE d.deleteFlag = 0")
    List<DinnerTable> findAllDinnerTableActive();
}
