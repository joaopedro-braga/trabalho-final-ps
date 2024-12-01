package com.depoisdosim.depoisdosim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depoisdosim.depoisdosim.models.Expense;
import com.depoisdosim.depoisdosim.services.ExpenseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseTest {

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void testCreateExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Despesa Mensal");
        expense.setDescription("Pagamento de contas mensais.");
        expense.setPrice(500.00);

        expenseService.create(expense);

        Expense savedExpense = expenseService.findById(expense.getId());
        assertNotNull(savedExpense.getId());
        assertEquals("Despesa Mensal", savedExpense.getTitle());
        assertEquals("Pagamento de contas mensais.", savedExpense.getDescription());
        assertEquals(500.00, savedExpense.getPrice());
    }
}
