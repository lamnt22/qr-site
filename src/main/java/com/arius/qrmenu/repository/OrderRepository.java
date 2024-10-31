package com.arius.qrmenu.repository;

import java.util.List;

import com.arius.qrmenu.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query ("SELECT o FROM Order o WHERE o.status = :status AND (o.tableName LIKE %:keyword% OR o.dishName LIKE %:keyword%)")
    Page<Order> search(@Param ("keyword") String keyword, @Param ("status") int status, Pageable pageable);

    @Query ("SELECT o FROM Order o WHERE o.tableId = ?1 AND o.paymentStatus = ?2")
    List<Order> findByTableIdAndPaymentStatus(int tableId, int paymentStatus);

    @Query ("SELECT o FROM Order o WHERE o.paymentStatus = ?1 AND o.paymentId = ?2")
    List<Order> findByPaymentStatusAndPaymentId(int paymentStatus, Long paymentId);
}
