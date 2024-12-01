package com.depoisdosim.depoisdosim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depoisdosim.depoisdosim.models.Task;
import com.depoisdosim.depoisdosim.services.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Tarefa Importante");
        task.setDescription("Realizar uma tarefa muito importante.");
        task.setDate("13/12/2023");
        task.setTime("20:00");
        task.setStatus("Pendente");

        taskService.create(task);

        Task savedTask = taskService.findById(task.getId());

        assertNotNull(savedTask.getId());
        assertEquals("Tarefa Importante", savedTask.getTitle());
        assertEquals("Realizar uma tarefa muito importante.", savedTask.getDescription());
        assertEquals("13/12/2023", savedTask.getDate());
        assertEquals("20:00", savedTask.getTime());
        assertEquals("Pendente", savedTask.getStatus());
    }
}
