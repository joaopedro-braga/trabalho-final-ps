package com.depoisdosim.depoisdosim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.Gift;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    
    @Query(value = "SELECT * FROM gift g WHERE g.wedding_id = :id", nativeQuery = true)
    List<Gift> findAllByWeddingId(@Param("id") Long id);

}
