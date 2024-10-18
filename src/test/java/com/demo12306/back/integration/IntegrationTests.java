package com.demo12306.back.integration;

import com.demo12306.back.entity.Ticket;
import com.demo12306.back.service.impl.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("integration-test")
public class IntegrationTests {

    @Autowired
    private CartService cartService;

    @BeforeAll
    public static void setUp() {
        // Set up Docker Compose services
        // This can be done using Testcontainers or a similar library
    }

    @Test
    public void testAddToCart() {
        String cartId = "testCart";
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTrainId(1001);
        ticket.setStartId(1);
        ticket.setEndId(2);
        ticket.setPrice(100);

        cartService.addToCart(cartId, ticket);

        List<Ticket> cart = cartService.getCart(cartId);
        assertEquals(1, cart.size());
        assertEquals(ticket.getId(), cart.get(0).getId());
    }

    @Test
    public void testRemoveFromCart() {
        String cartId = "testCart";
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTrainId(1001);
        ticket.setStartId(1);
        ticket.setEndId(2);
        ticket.setPrice(100);

        cartService.addToCart(cartId, ticket);
        cartService.removeFromCart(cartId, ticket.getId());

        List<Ticket> cart = cartService.getCart(cartId);
        assertTrue(cart.isEmpty());
    }
}
