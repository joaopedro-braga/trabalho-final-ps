package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Guest;
import com.depoisdosim.depoisdosim.repositories.GuestRepository;

@Service
public class GuestService {
    
    @Autowired
    private GuestRepository guestRepository;

    public Guest findById(Long id) {
        Optional<Guest> Guest = this.guestRepository.findById(id);
        return Guest.orElseThrow(() -> new RuntimeException("Convidado não encontrado! Id: " + id + ", Tipo: " + Guest.class.getName()));
    }

    public List<Guest> findAllByWeddingId(Long weddingId) {
        List<Guest> guests = this.guestRepository.findAllByWeddingId(weddingId);
        return guests;
    }

    @Transactional
    public Guest create(Guest obj) {
        obj.setId(null);
        obj = this.guestRepository.save(obj);
        return obj;
    }

    @Transactional
    public Guest update(Guest obj) {
        Guest newObj = this.findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        return this.guestRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.guestRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir o convidado!");
        }
    }

}
