package com.arius.qrmenu.controller;

import java.util.List;

import com.arius.qrmenu.form.StaffForm;
import com.arius.qrmenu.model.Staff;
import com.arius.qrmenu.service.StaffService;

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
@RequestMapping ("/api/staff")
@CrossOrigin
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping ("")
    public ResponseEntity<Page<Staff>> findAllStaff(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<Staff>>(staffService.findAll(sortBy, page, size), HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<?> insertStaff(@RequestBody final StaffForm staffForm) {

        try {
            Staff staff = new Staff();
            BeanUtils.copyProperties(staffForm, staff);
            Staff insertStaff = staffService.save(staff);
            return new ResponseEntity<Staff>(insertStaff, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable ("id") final Long id, @RequestBody StaffForm staffForm) {
        try {
            Staff staff = new Staff();
            BeanUtils.copyProperties(staffForm, staff);
            if (id != null) {
                Staff updateStaff = staffService.updateStaff(id, staff);
                return new ResponseEntity<Staff>(updateStaff, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable ("id") final Long id) {

        if (id != null) {
            return new ResponseEntity<Staff>(staffService.findById(id)
                .get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Staff> deleteStaff(@PathVariable ("id") final Long id) {

        if (id != null) {
            return new ResponseEntity<Staff>(staffService.deletStaff(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/search")
    public ResponseEntity<Page<Staff>> search(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy,
        @RequestParam ("keyword") final String keyword) {

        return new ResponseEntity<Page<Staff>>(staffService.findByKeywordAndNotDeleted(keyword, sortBy, page, size),
            HttpStatus.OK);
    }

    @GetMapping ("/staffs-active")
    public ResponseEntity<List<Staff>> getAllStaff() {

        return new ResponseEntity<List<Staff>>(staffService.getStaffs(), HttpStatus.OK);
    }
}
