package com.depoisdosim.depoisdosim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depoisdosim.depoisdosim.models.Gift;
import com.depoisdosim.depoisdosim.services.GiftService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GiftTest {

    @Autowired
    private GiftService giftService;

    @Test
    public void testNewGift() {
    
        Gift gift = new Gift();

        gift.setId(1L);
        gift.setName("Presente 1");
        gift.setDescription("Um presente muito legal.");
        gift.setAvailable(true);
        gift.setPrice(100.00);
        gift.setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fveja.abril.com.br%2Fcoluna%2Fsobre-palavras%2Fa-diferenca-entre-o-presente-e-o-presente&psig=AOvVaw1iC6ZKBNOjOHA2P7a8pnOx&ust=1701284159434000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNjmzJWv54IDFQAAAAAdAAAAABAE");
        
        giftService.create(gift);

        Gift savedGift = giftService.findById(gift.getId());
        assertEquals(gift.getName(), savedGift.getName());
        assertEquals(gift.getDescription(), savedGift.getDescription());
        assertEquals(gift.getPrice(), savedGift.getPrice());
    }
}
