package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.domain.user.UserRole;
import com.depoisdosim.depoisdosim.models.User;
import com.depoisdosim.depoisdosim.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    public List<User> findUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = this.findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir um usuário que possui registros de presentes, convidados ou casamentos!");
        }
    }

    // Role Supplier
    public List<User> findUsersByRoleAndEmail(UserRole role, String email) {
        return userRepository.findByRoleAndEmail(role, email);
    }

}
