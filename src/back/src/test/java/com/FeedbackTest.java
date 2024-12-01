package com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depoisdosim.depoisdosim.models.Feedback;
import com.depoisdosim.depoisdosim.services.FeedbackService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FeedbackTest {

    @Autowired
    private FeedbackService feedbackService;

    @Test
    public void testCreateFeedback() {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setDescription("Ótimo serviço!");
        feedback.setRating(5.0);

        feedbackService.create(feedback);

        Feedback savedFeedback = feedbackService.findById(feedback.getId());
        assertNotNull(savedFeedback.getId());
        assertEquals("Ótimo serviço!", savedFeedback.getDescription());
        assertEquals(5.0, savedFeedback.getRating());
    }
}
