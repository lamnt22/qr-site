package com.arius.qrmenu.service;

import java.util.List;
import java.util.Optional;

import com.arius.qrmenu.model.Staff;
import com.arius.qrmenu.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository repository;

    @Override
    public Staff save(Staff entity) {
        return repository.save(entity);
    }

    @Override
    public Page<Staff> findAll(String sortBy, int page, int size) {
        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        return repository.findAllStaff(pageable);
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Staff exitstingStaff = repository.findById(id)
            .get();
        exitstingStaff.setName(staff.getName());
        exitstingStaff.setPhoneNumber(staff.getPhoneNumber());
        exitstingStaff.setStatus(staff.getStatus());

        return repository.save(exitstingStaff);
    }

    @Override
    public Page<Staff> findByKeywordAndNotDeleted(String keyword, String sortBy, int page, int size) {

        String[] sortParams = sortBy.split(",");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1
            ? Sort.Direction.fromString(sortParams[1])
            : Sort.Direction.DESC;

        // Construct Sort object
        Sort sortObj = Sort.by(sortDirection, sortProperty);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);
        return repository.findByKeywordAndNotDeleted(keyword, pageable);
    }

    @Override
    public Staff deletStaff(Long id) {
        Staff exitstingStaff = repository.findById(id)
            .get();
        exitstingStaff.setDeleteFlag(1);

        return repository.save(exitstingStaff);
    }

    @Override
    public List<Staff> getStaffs() {
        return repository.findAllStaffsNotDeleted();
    }
}
