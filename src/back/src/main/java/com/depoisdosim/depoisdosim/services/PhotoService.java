package com.depoisdosim.depoisdosim.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Photo;
import com.depoisdosim.depoisdosim.repositories.PhotoRepository;

import jakarta.validation.Valid;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public Photo findById(Long id) {
        Optional<Photo> photo = this.photoRepository.findById(id);
        return photo.orElseThrow(() -> new RuntimeException("Foto não encontrada! Id: " + id + ", Tipo: " + Photo.class.getName()));
    }

    public List<Photo> findAllByWeddingId(Long weddingId) {
        List<Photo> photos = this.photoRepository.findAllByWeddingId(weddingId);
        return photos;
    }

    @Transactional
    public Photo create(Photo obj) {
        obj.setId(null);
        obj = this.photoRepository.save(obj);
        return obj;
    }

    @Transactional
    public Photo update(Photo obj) {
        Photo newObj = this.findById(obj.getId());
        newObj.setLink(obj.getLink());
        return this.photoRepository.save(newObj);
    }
    
    public void delete(Long id) {
        this.findById(id);
        try {
            this.photoRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir a foto!");
        }
    }

    public @Valid Photo insert(@Valid Photo obj) {
        return null;
    }

    public void updateWeddingFirstPhotoPostDate(Long id, LocalDateTime createdAt) {
    }
}
