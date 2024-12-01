package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.depoisdosim.depoisdosim.domain.others.PhotoDTO;
import com.depoisdosim.depoisdosim.models.Photo;
import com.depoisdosim.depoisdosim.services.PhotoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<Photo> findById(@PathVariable Long id) {
        Photo obj = this.photoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<PhotoDTO>> findAllByWeddingId(@PathVariable Long weddingId) {
        List<Photo> photos = this.photoService.findAllByWeddingId(weddingId);

        List<PhotoDTO> photoDTOs = photos.stream()
                .map(photo -> {
                    PhotoDTO dto = new PhotoDTO();
                    dto.setId(photo.getId());
                    dto.setLink(photo.getLink());
                    dto.setWedding(photo.getWedding().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(photoDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Photo obj) {
        this.photoService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Photo obj, @PathVariable Long id) {
        obj.setId(id);
        this.photoService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.photoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
