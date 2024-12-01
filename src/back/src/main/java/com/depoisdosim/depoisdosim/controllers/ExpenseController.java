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

import com.depoisdosim.depoisdosim.domain.others.ExpenseDTO;
import com.depoisdosim.depoisdosim.models.Expense;
import com.depoisdosim.depoisdosim.services.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable Long id) {
        Expense obj = this.expenseService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<ExpenseDTO>> findAllByWeddingId(@PathVariable Long weddingId) {
        List<Expense> expenses = this.expenseService.findAllByWeddingId(weddingId);

        List<ExpenseDTO> expenseDTOs = expenses.stream()
                .map(expense -> {
                    ExpenseDTO dto = new ExpenseDTO();
                    dto.setId(expense.getId());
                    dto.setTitle(expense.getTitle());
                    dto.setDescription(expense.getDescription());
                    dto.setPrice(expense.getPrice());
                    dto.setWedding(expense.getWedding().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(expenseDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Expense obj) {
        this.expenseService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Expense obj, @PathVariable Long id) {
        obj.setId(id);
        this.expenseService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}