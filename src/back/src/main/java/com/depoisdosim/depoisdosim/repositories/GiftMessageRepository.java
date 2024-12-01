package com.depoisdosim.depoisdosim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.GiftMessage;

@Repository
public interface GiftMessageRepository extends JpaRepository<GiftMessage, Long> {
    
    @Query(value = "SELECT * FROM gift_message g WHERE g.wedding_id = :id", nativeQuery = true)
    List<GiftMessage> findAllByWeddingId(@Param("id") Long id);

}
