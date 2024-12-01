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

import com.depoisdosim.depoisdosim.domain.others.FeedbackDTO;
import com.depoisdosim.depoisdosim.models.Feedback;
import com.depoisdosim.depoisdosim.services.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
@Validated
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> findById(@PathVariable Long id) {
        Feedback obj = this.feedbackService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<FeedbackDTO>> findAllBySupplierId(@PathVariable Long supplierId) {
        List<Feedback> feedbacks = this.feedbackService.findAllBySupplierId(supplierId);

        List<FeedbackDTO> feedbackDTOs = feedbacks.stream()
                .map(feedback -> {
                    FeedbackDTO dto = new FeedbackDTO();
                    dto.setId(feedback.getId());
                    dto.setDescription(feedback.getDescription());
                    dto.setRating(feedback.getRating());
                    dto.setUser(feedback.getUser().getId());
                    dto.setSupplier(feedback.getSupplier().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(feedbackDTOs);
    }

    // Avaliações suppliers
    @GetMapping("/all-suppliers")
    public ResponseEntity<List<FeedbackDTO>> getAllSupplierFeedbacks() {
        List<Feedback> supplierFeedbacks = this.feedbackService.getAllSupplierFeedbacks();
    
        List<FeedbackDTO> feedbackDTOs = supplierFeedbacks.stream()
                .map(feedback -> {
                    FeedbackDTO dto = new FeedbackDTO();
                    dto.setId(feedback.getId());
                    dto.setDescription(feedback.getDescription());
                    dto.setRating(feedback.getRating());
                    dto.setUser(feedback.getUser().getId());
                    dto.setSupplier(feedback.getSupplier() != null ? feedback.getSupplier().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    
        return ResponseEntity.ok().body(feedbackDTOs);
    }
    

    // Avaliações site
    @GetMapping("/nullSupplier")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksWithNullSupplier() {
        List<Feedback> feedbacks = this.feedbackService.findAllByNullSupplier();

        List<FeedbackDTO> feedbackDTOs = feedbacks.stream()
                .map(feedback -> {
                    FeedbackDTO dto = new FeedbackDTO();
                    dto.setId(feedback.getId());
                    dto.setDescription(feedback.getDescription());
                    dto.setRating(feedback.getRating());
                    dto.setUser(feedback.getUser().getId());
                    dto.setSupplier(feedback.getSupplier() != null ? feedback.getSupplier().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(feedbackDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Feedback obj) {
        this.feedbackService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Feedback obj, @PathVariable Long id) {
        obj.setId(id);
        this.feedbackService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
