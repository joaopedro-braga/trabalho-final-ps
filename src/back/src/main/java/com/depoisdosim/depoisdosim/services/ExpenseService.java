package com.depoisdosim.depoisdosim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depoisdosim.depoisdosim.models.Expense;
import com.depoisdosim.depoisdosim.repositories.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense findById(Long id) {
        Optional<Expense> Expense = this.expenseRepository.findById(id);
        return Expense.orElseThrow(() -> new RuntimeException("Despesa não encontrada! Id: " + id + ", Tipo: " + Expense.class.getName()));
    }

    public List<Expense> findAllByWeddingId(Long weddingId) {
        List<Expense> expenses = this.expenseRepository.findAllByWeddingId(weddingId);
        return expenses;
    }

    @Transactional
    public Expense create(Expense obj) {
        obj.setId(null);
        obj = this.expenseRepository.save(obj);
        return obj;
    }

    @Transactional
    public Expense update(Expense obj) {
        Expense newObj = this.findById(obj.getId());
        newObj.setTitle(obj.getTitle());
        newObj.setDescription(obj.getDescription());
        newObj.setPrice(obj.getPrice());
        return this.expenseRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.expenseRepository.deleteById(id);
        } catch(Exception e) {
            throw new RuntimeException("Não é possível excluir a despesa!");
        }
    }
    
}
