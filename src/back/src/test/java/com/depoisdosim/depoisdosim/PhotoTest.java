package com.depoisdosim.depoisdosim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhotoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddPhotoToDisplay() throws Exception {
        String jsonContent = "{ \"imageUrl\": \"https://www.example.com/image.jpg\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/photo/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
