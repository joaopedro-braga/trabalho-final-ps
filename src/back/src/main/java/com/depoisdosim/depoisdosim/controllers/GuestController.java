package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;

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

import com.depoisdosim.depoisdosim.domain.others.GuestDTO;
import com.depoisdosim.depoisdosim.models.Guest;
import com.depoisdosim.depoisdosim.services.GuestService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/guest")
public class GuestController {
    
    @Autowired
    private GuestService guestService;

    @GetMapping("/{id}")
    public ResponseEntity<Guest> findById(@PathVariable Long id) {
        Guest obj = this.guestService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<GuestDTO>> findAllByWeddingId(@PathVariable Long weddingId) {
        List<Guest> guests = this.guestService.findAllByWeddingId(weddingId);

        List<GuestDTO> guestDTOs = guests.stream()
                .map(guest -> {
                    GuestDTO dto = new GuestDTO();
                    dto.setId(guest.getId());
                    dto.setName(guest.getName());
                    dto.setEmail(guest.getEmail());
                    dto.setNumPeople(guest.getNumPeople());
                    dto.setNamePeople(guest.getNamePeople());
                    dto.setWedding(guest.getWedding().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(guestDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Guest obj) {
        this.guestService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Guest obj, @PathVariable Long id) {
        obj.setId(id);
        this.guestService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Enviar e-mail
    @PostMapping("/wedding/{weddingId}/invite/{username}")
    public ResponseEntity<Void> sendInvite(@PathVariable Long weddingId, @PathVariable String username) {
        List<Guest> guests = this.guestService.findAllByWeddingId(weddingId);
        for (Guest guest : guests) {
            String to = guest.getEmail();
            String from = "convite@depoisdosim.com.br";
            String host = "smtp-relay.brevo.com";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");

            Session session = Session.getDefaultInstance(properties);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Você foi convidado para o casamento de " + username);
                message.setText(String.format("Olá, %s!\nEstamos muito felizes em contar que você recebeu um convite para o casamento de %s", guest.getName(), username));

                Transport.send(message, "lvcarolina42@gmail.com", "8KHgYBFI3sf2pzrL");
                System.out.println("Sent message successfully....");
            } catch (Exception mex) {
                mex.printStackTrace();
            }
        }

        return ResponseEntity.ok().build();
    }
        
}
