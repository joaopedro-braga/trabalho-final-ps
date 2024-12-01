package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.depoisdosim.depoisdosim.domain.others.GiftDTO;
import com.depoisdosim.depoisdosim.models.Gift;
import com.depoisdosim.depoisdosim.models.Gift.CreateGift;
import com.depoisdosim.depoisdosim.services.GiftService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/gift")
@Validated
public class GiftController {
    
    @Autowired
    private GiftService giftService;

    @GetMapping("/{id}")
    public ResponseEntity<Gift> findById(@PathVariable Long id) {
        Gift obj = this.giftService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<GiftDTO>> findAllByWeddingId(@PathVariable Long weddingId) {
        List<Gift> gifts = this.giftService.findAllByWeddingId(weddingId);

        List<GiftDTO> giftDTOs = gifts.stream()
                .map(gift -> {
                    GiftDTO dto = new GiftDTO();
                    dto.setId(gift.getId());
                    dto.setName(gift.getName());
                    dto.setDescription(gift.getDescription());
                    dto.setAvailable(gift.getAvailable());
                    dto.setPrice(gift.getPrice());
                    dto.setImage(gift.getImage());
                    dto.setWedding(gift.getWedding().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(giftDTOs);
    }

    @PostMapping
    @Validated(CreateGift.class)
    public ResponseEntity<Void> create(@Valid @RequestBody Gift obj) {
        this.giftService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(CreateGift.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Gift obj, @PathVariable Long id) {
        obj.setId(id);
        this.giftService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAvailable(@RequestBody Gift obj, @PathVariable Long id) {
        obj.setId(id);
        Boolean available = obj.getAvailable();
        giftService.updateAvailable(obj, available);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.giftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
