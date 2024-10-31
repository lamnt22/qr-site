package com.arius.qrmenu.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.common.Constants;
import com.arius.qrmenu.model.DinnerTable;
import com.arius.qrmenu.repository.DinnerTableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DinnerTableServiceImpl implements DinnerTableService {

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Override
    public void deleteById(int id) {
        DinnerTable existingTable = dinnerTableRepository.findById(id)
            .get();
        existingTable.setDeleteFlag(1);
        dinnerTableRepository.save(existingTable);
    }

    @Override
    public long count() {
        return dinnerTableRepository.count();
    }

    @Override
    public Optional<DinnerTable> findById(int id) {
        return dinnerTableRepository.findById(id);
    }

    @Override
    public Page<DinnerTable> findAll(Pageable pageable) {
        return dinnerTableRepository.findAll(pageable);
    }

    @Override
    public List<DinnerTable> findAll(Sort sort) {
        return dinnerTableRepository.findAll(sort);
    }

    @Override
    public ResponseEntity<?> save(DinnerTable dinnerTable) {
        List<String> errors = new ArrayList<>();

        Optional<DinnerTable> existingTable = findDinnerTableByName(dinnerTable.getName());

        if (existingTable.isPresent()) {
            errors.add(Constants.DinnerTableValidation.TABLE_NAME_EXIST);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        dinnerTableRepository.save(dinnerTable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Optional<DinnerTable> findDinnerTableByName(String name) {
        return dinnerTableRepository.findByName(name);
    }

    @Override
    public ResponseEntity<?> updateDinnerTable(int id, DinnerTable dinnerTable) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        List<String> errors = new ArrayList<>();

        try {
            Optional<DinnerTable> existingTableWithName = findDinnerTableByName(dinnerTable.getName());
            Optional<DinnerTable> existingTable = dinnerTableRepository.findById(id);

            if (!existingTable.isPresent()) errors.add("Dinner table with id " + id + " does not exist");

            if (existingTableWithName.isPresent() && !existingTableWithName.get()
                .getName()
                .equals(existingTable.get()
                    .getName()))
                errors.add(Constants.DinnerTableValidation.TABLE_NAME_EXIST);

            if (errors.size() > 0) return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

            existingTable.get()
                .setName(dinnerTable.getName());
            existingTable.get()
                .setSeatNumber(dinnerTable.getSeatNumber());
            existingTable.get()
                .setStatus(dinnerTable.getStatus());
            existingTable.get()
                .setDescription(dinnerTable.getDescription());
            existingTable.get()
                .setUpdatedAt(dateFormat.format(date));

            dinnerTableRepository.save(existingTable.get());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            errors.add(e.toString());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Page<DinnerTable> search(int page, int size, String sortValue, String name, String status) {

        try {
            String[] sortParams = sortValue.split(",");
            String sortProperty = sortParams[0];
            Sort.Direction sortDirection = sortParams.length > 1
                ? Sort.Direction.fromString(sortParams[1])
                : Sort.Direction.DESC;

            // Construct Sort object
            Sort sortObj = Sort.by(sortDirection, sortProperty);
            Pageable pageable = PageRequest.of(page - 1, size, sortObj);

            Page<DinnerTable> tables = dinnerTableRepository.findByNameAndStatus(name, status, pageable);
            return tables;
        } catch (Exception e) {
            System.out.println("error: " + e.toString());
            throw new Error("Get Page Data Fall");
        }

    }

    @Override
    public List<DinnerTable> findAllDinnerTableActive() {
        return dinnerTableRepository.findAllDinnerTableActive();
    }

}
