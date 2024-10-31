package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Staff;

import org.springframework.data.domain.Page;

public interface StaffService {

    void deleteById(Long id);

    Optional<Staff> findById(Long id);

    Page<Staff> findAll(String sortBy, int page, int size);

    Staff save(Staff entity);

    Staff updateStaff(Long id, Staff staff);

    Page<Staff> findByKeywordAndNotDeleted(String keyword, String sortBy, int page, int size);

    Staff deletStaff(Long id);

    List<Staff> getStaffs();

}
