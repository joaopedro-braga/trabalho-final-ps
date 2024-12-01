package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Wedding;
import com.depoisdosim.depoisdosim.repositories.WeddingRepository;

@Service
public class WeddingService {
    
    @Autowired
    private WeddingRepository weddingRepository;

    public Wedding findById(Long id) {
        Optional<Wedding> Wedding = this.weddingRepository.findById(id);
        return Wedding.orElseThrow(() -> new RuntimeException("Casamento não encontrado! Id: " + id + ", Tipo: " + Wedding.class.getName()));
    }

    public List<Wedding> findAll() {
        return weddingRepository.findAll();
    }

    @Transactional
    public Wedding create(Wedding obj) {
        obj.setId(null);
        obj = this.weddingRepository.save(obj);
        return obj;
    }

    @Transactional
    public Wedding update(Wedding obj) {
        Wedding newObj = this.findById(obj.getId());
        return this.weddingRepository.save(newObj);
    }

    @Transactional
    public Wedding updateBudgetExceeded(Wedding obj) {
        Wedding newObj = this.findById(obj.getId());
        newObj.setBudgetExceeded(obj.getBudgetExceeded());
        return this.weddingRepository.save(newObj);
    }

    @Transactional
    public Wedding updateFinishedStatus(Wedding obj) {
        Wedding newObj = this.findById(obj.getId());
        newObj.setFinished(obj.getFinished());
        return this.weddingRepository.save(newObj);
    }   
    
    public void delete(Long id) {
        this.findById(id);
        try {
            this.weddingRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir um casamento que está sendo utilizado!");
        }
    }

}
