package com.depoisdosim.depoisdosim.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    @Query(value = "SELECT * FROM task g WHERE g.user_id = :id", nativeQuery = true)
    List<Task> findAllByUserId(@Param("id") Long id);
        
}
