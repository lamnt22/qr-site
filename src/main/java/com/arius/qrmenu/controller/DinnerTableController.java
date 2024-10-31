package com.arius.qrmenu.controller;

import java.util.List;

import com.arius.qrmenu.model.DinnerTable;
import com.arius.qrmenu.service.DinnerTableService;

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
@RequestMapping ("/api/dinner-table")
@CrossOrigin
public class DinnerTableController {

    @Autowired
    private DinnerTableService dinnerTableService;

    @GetMapping ("/search")
    public ResponseEntity<Page<DinnerTable>> search(@RequestParam ("page") int page, @RequestParam ("size") int size,
        @RequestParam ("sortValue") String sortValue, @RequestParam ("name") String name,
        @RequestParam ("status") String status) {

        Page<DinnerTable> dinnerTables = dinnerTableService.search(page, size, sortValue, name, status);
        return new ResponseEntity<Page<DinnerTable>>(dinnerTables, HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<?> addDinnerTable(@RequestBody DinnerTable dinnerTable) {
        return dinnerTableService.save(dinnerTable);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateDinnerTable(@RequestBody DinnerTable dinnerTable, @PathVariable int id) {
        return dinnerTableService.updateDinnerTable(id, dinnerTable);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> detailDinnerTable(@PathVariable int id) {
        return new ResponseEntity<>(dinnerTableService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteDinnerTable(@PathVariable int id) {
        dinnerTableService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping ("/getDinnerTableActive")
    public ResponseEntity<List<DinnerTable>> getDinnerTableActive() {
        return new ResponseEntity<List<DinnerTable>>(dinnerTableService.findAllDinnerTableActive(), HttpStatus.OK);
    }
}
