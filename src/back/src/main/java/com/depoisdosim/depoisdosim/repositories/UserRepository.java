package com.depoisdosim.depoisdosim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.depoisdosim.depoisdosim.domain.user.UserRole;
import com.depoisdosim.depoisdosim.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);

    List<User> findByRole(UserRole role);

    List<User> findByRoleAndEmail(UserRole role, String email);
    
}
