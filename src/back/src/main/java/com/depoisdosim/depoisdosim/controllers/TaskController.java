package com.depoisdosim.depoisdosim.controllers;

import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.depoisdosim.depoisdosim.domain.others.TaskDTO;
import com.depoisdosim.depoisdosim.models.Task;
import com.depoisdosim.depoisdosim.services.TaskService;

import javax.mail.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> findAllByUserId(@PathVariable Long userId) {
        List<Task> tasks = this.taskService.findAllByUserId(userId);

        List<TaskDTO> taskDTOs = tasks.stream()
                .map(task -> {
                    TaskDTO dto = new TaskDTO();
                    dto.setId(task.getId());
                    dto.setTitle(task.getTitle());
                    dto.setDescription(task.getDescription());
                    dto.setDate(task.getDate());
                    dto.setTime(task.getTime());
                    dto.setStatus(task.getStatus());
                    dto.setUser(task.getUser().getId());

                    // Verifica se supplier é nulo e atribui null ou o ID
                    if (task.getSupplier() != null) {
                        dto.setSupplier(task.getSupplier().getId());
                    } else {
                        dto.setSupplier(null);
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(taskDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> findAllTasks(@RequestParam("supplier") Long supplierId) {
    List<Task> tasks = this.taskService.findAllTasks();

    List<TaskDTO> taskDTOs = tasks.stream()
        .map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setDate(task.getDate());
            dto.setTime(task.getTime());
            dto.setStatus(task.getStatus());
            dto.setUser(task.getUser().getId());

            if (task.getSupplier() != null) {
            dto.setSupplier(task.getSupplier().getId());
            } else {
            dto.setSupplier(null);
            }

            return dto;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok().body(taskDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) {
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/confirmar")
    public void confirmarPresenca(@PathVariable Long taskId) {
        taskService.confirmarPresenca(taskId);
    }

    @PostMapping("/{taskId}/negar")
    public void negarPresenca(@PathVariable Long taskId) {
        taskService.negarPresenca(taskId);
    }

    // Enviar e-mail
    @PostMapping("/invite/{username}/{emailSupllier}/{date}/{time}/{title}/{description}")
    public ResponseEntity<Void> sendInvite(@PathVariable String username, @PathVariable String emailSupllier, @PathVariable String date, @PathVariable String time, @PathVariable String title, @PathVariable String description) {
            String from = "convite@depoisdosim.com.br";
            String host = "smtp-relay.brevo.com";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");

            Session session = Session.getDefaultInstance(properties);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailSupllier));
                message.setSubject("Convite para marcar compromisso");
                message.setText("Olá, " + username + "! Você foi convidado para marcar um compromisso no dia " + date + " às " + time + ".\n\nTítulo: " + title + "\nDescrição: " + description + "\n\nAcesse o site para confirmar ou negar o compromisso: https://depoisdosim.com.br");

                Transport.send(message, "lvcarolina42@gmail.com", "8KHgYBFI3sf2pzrL");
                System.out.println("Sent message successfully....");
            } catch (Exception mex) {
                mex.printStackTrace();
            }

        return ResponseEntity.ok().build();
    }

}
