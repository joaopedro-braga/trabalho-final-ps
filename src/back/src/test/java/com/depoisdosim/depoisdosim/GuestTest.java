package com.depoisdosim.depoisdosim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depoisdosim.depoisdosim.models.Guest;
import com.depoisdosim.depoisdosim.services.GuestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GuestTest {

    @Autowired
    private GuestService guestService;

    @Test
    public void testRegisterGuest() {
        Guest guest = new Guest();
        guest.setId(1L);
        guest.setName("Fulano Santos");
        guest.setEmail("fulansantos@example.com");
        guest.setNumPeople(2);
        guest.setNamePeople("Cicrano e Beltrano");

        guestService.create(guest);

        Guest savedGuest = guestService.findById(guest.getId());
        assertNotNull(savedGuest.getId());
        assertEquals("Fulano Santos", savedGuest.getName());
        assertEquals("fulanosantos@example.com", savedGuest.getEmail());
        assertEquals(2, savedGuest.getNumPeople());
        assertEquals("Cicrano e Beltrano", savedGuest.getNamePeople());
    }
}
