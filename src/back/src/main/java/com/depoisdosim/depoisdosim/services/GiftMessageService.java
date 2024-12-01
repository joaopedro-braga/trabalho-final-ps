package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.GiftMessage;
import com.depoisdosim.depoisdosim.repositories.GiftMessageRepository;

@Service
public class GiftMessageService {

    @Autowired
    private GiftMessageRepository giftMessageRepository;

    public GiftMessage findById(Long id) {
        Optional<GiftMessage> giftMessages = this.giftMessageRepository.findById(id);
        return giftMessages.orElseThrow(() -> new RuntimeException("Mensagem não encontrada! Id: " + id + ", Tipo: " + GiftMessage.class.getName()));
    }

    public List<GiftMessage> findAllByWeddingId(Long weddingId) {
        List<GiftMessage> giftMessages = this.giftMessageRepository.findAllByWeddingId(weddingId);
        return giftMessages;
    }

    @Transactional
    public GiftMessage create(GiftMessage obj) {
        obj.setId(null);
        obj = this.giftMessageRepository.save(obj);
        return obj;
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.giftMessageRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir a mensagem!");
        }
    }

}
