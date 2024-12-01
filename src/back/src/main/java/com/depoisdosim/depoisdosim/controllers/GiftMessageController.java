package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.depoisdosim.depoisdosim.domain.others.GiftMessageDTO;
import com.depoisdosim.depoisdosim.models.GiftMessage;
import com.depoisdosim.depoisdosim.services.GiftMessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/giftMessage")
public class GiftMessageController {
    
    @Autowired
    private GiftMessageService giftMessageService;

    @GetMapping("/{id}")
    public ResponseEntity<GiftMessage> findById(@PathVariable Long id) {
        GiftMessage obj = this.giftMessageService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<GiftMessageDTO>> findAllByWeddingId(@PathVariable Long weddingId) {
        List<GiftMessage> giftMessages = this.giftMessageService.findAllByWeddingId(weddingId);

        List<GiftMessageDTO> giftMessageDTOs = giftMessages.stream()
                .map(giftMessage -> {
                    GiftMessageDTO dto = new GiftMessageDTO();
                    dto.setId(giftMessage.getId());
                    dto.setName(giftMessage.getName());
                    dto.setEmail(giftMessage.getEmail());
                    dto.setDescription(giftMessage.getDescription());
                    dto.setGiftName(giftMessage.getGift().getName());
                    dto.setWeddingId(giftMessage.getWedding().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(giftMessageDTOs);
    }



    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody GiftMessage obj) {
        this.giftMessageService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
