package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.depoisdosim.depoisdosim.domain.others.UserDTO;
import com.depoisdosim.depoisdosim.domain.user.UserRole;
import com.depoisdosim.depoisdosim.models.User;
import com.depoisdosim.depoisdosim.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = this.userService.findById(id);
    
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setWedding((user.getWedding() != null) ? user.getWedding().getId() : null);
    
        return ResponseEntity.ok().body(dto);
    }    

    // Encontrar todos User com role supplier
    @GetMapping("/supplier/all")
    public ResponseEntity<List<UserDTO>> findAllSupplierUsers() {
        List<User> supplierUsers = userService.findUsersByRole(UserRole.SUPPLIER);
    
        List<UserDTO> supplierDTOs = supplierUsers.stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setPassword(user.getPassword());
                    dto.setRole(user.getRole());
                    dto.setEmail(user.getEmail());
                    dto.setWedding((user.getWedding() != null) ? user.getWedding().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    
        return ResponseEntity.ok().body(supplierDTOs);
    }
    
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody User obj) {
        this.userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id) {
        obj.setId(id);
        this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Role Supplier
    @GetMapping("/supplier/email/{email}")
    public ResponseEntity<List<UserDTO>> findSupplierByEmail(@PathVariable String email) {
        List<User> supplierUsers = userService.findUsersByRoleAndEmail(UserRole.SUPPLIER, email);

        List<UserDTO> supplierDTOs = supplierUsers.stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setPassword(user.getPassword());
                    dto.setRole(user.getRole());
                    dto.setEmail(user.getEmail());
                    dto.setWedding((user.getWedding() != null) ? user.getWedding().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(supplierDTOs);
    }
}
