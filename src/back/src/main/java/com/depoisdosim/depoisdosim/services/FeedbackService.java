package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Feedback;
import com.depoisdosim.depoisdosim.repositories.FeedbackRepository;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback findById(Long id) {
        Optional<Feedback> feedback = this.feedbackRepository.findById(id);
        return feedback.orElseThrow(() -> new RuntimeException("Feedback não encontrado! Id: " + id + ", Tipo: " + Feedback.class.getName()));
    }

    public List<Feedback> findAllByUserId(Long userId) {
        List<Feedback> feedbacks = this.feedbackRepository.findAllByUserId(userId);
        return feedbacks;
    }

    public List<Feedback> findAllBySupplierId(Long supplierId) {
        List<Feedback> feedbacks = this.feedbackRepository.findAllBySupplierId(supplierId);
        return feedbacks;
    }

    public List<Feedback> getAllSupplierFeedbacks() {
        return feedbackRepository.findAllBySupplierNotNull();
    }    

    public List<Feedback> findAllByNullSupplier() {
        return feedbackRepository.findAllBySupplierIsNull();
    }

    @Transactional
    public Feedback create(Feedback obj) {
        obj.setId(null);
        obj = this.feedbackRepository.save(obj);
        return obj;
    }

    @Transactional
    public Feedback update(Feedback obj) {
        Feedback newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setRating(obj.getRating());
        return this.feedbackRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.feedbackRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir um feedback que está sendo exposto!");
        }
    }

}
