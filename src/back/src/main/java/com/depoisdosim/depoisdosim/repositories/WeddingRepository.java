package com.depoisdosim.depoisdosim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.Wedding;

@Repository
public interface WeddingRepository extends JpaRepository<Wedding, Long> {
    
}
