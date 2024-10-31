package com.arius.qrmenu.repository;

import java.util.List;

import com.arius.qrmenu.model.Staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query ("SELECT s FROM Staff s WHERE (s.name LIKE %:keyword% OR s.phoneNumber LIKE %:keyword%) AND s.deleteFlag = 0")
    Page<Staff> findByKeywordAndNotDeleted(String keyword, Pageable pageable);

    @Query ("SELECT s FROM Staff s WHERE s.deleteFlag = 0")
    Page<Staff> findAllStaff(Pageable pageable);
    
    @Query ("SELECT s FROM Staff s WHERE s.deleteFlag = 0")
    List<Staff> findAllStaffsNotDeleted();
}
