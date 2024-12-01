package com.depoisdosim.depoisdosim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query(value = "SELECT * FROM guest g WHERE g.wedding_id = :id", nativeQuery = true)
    List<Guest> findAllByWeddingId(@Param("id") Long id);

}
