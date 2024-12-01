package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Gift;
import com.depoisdosim.depoisdosim.repositories.GiftRepository;

@Service
public class GiftService {
    
    @Autowired
    private GiftRepository giftRepository;

    public Gift findById(Long id) {
        Optional<Gift> gift = this.giftRepository.findById(id);
        return gift.orElseThrow(() -> new RuntimeException("Presente não encontrado! Id: " + id + ", Tipo: " + Gift.class.getName()));
    }

    public List<Gift> findAllByWeddingId(Long weddingId) {
        List<Gift> gifts = this.giftRepository.findAllByWeddingId(weddingId);
        return gifts;
    }

    @Transactional
    public Gift create(Gift obj) {
        obj.setId(null);
        obj = this.giftRepository.save(obj);
        return obj;
    }

    @Transactional
    public Gift update(Gift obj) {
        Gift newObj = this.findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setDescription(obj.getDescription());
        newObj.setPrice(obj.getPrice());
        newObj.setAvailable(obj.getAvailable());
        newObj.setImage(obj.getImage());
        return this.giftRepository.save(newObj);
    }

    @Transactional
    public Gift updateAvailable(Gift obj, Boolean available) {
        Gift newObj = this.findById(obj.getId());
        newObj.setAvailable(available);
        return this.giftRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.giftRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir um presente que está sendo utilizado em um casamento!");
        }
    }

}
