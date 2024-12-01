package com.depoisdosim.depoisdosim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    @Query(value = "SELECT * FROM feedback f WHERE f.user_id = :id", nativeQuery = true)
    List<Feedback> findAllByUserId(@Param("id") Long id);

    @Query(value = "SELECT * FROM feedback f WHERE f.supplier_id = :id", nativeQuery = true)
    List<Feedback> findAllBySupplierId(@Param("id") Long id);

    List<Feedback> findAllBySupplierNotNull();

    List<Feedback> findAllBySupplierIsNull();

}