package com.demo12306.back.controller;

import com.demo12306.back.common.R;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.service.impl.CartService;
import com.demo12306.back.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.WebUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setUserId(1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(JwtUtils.getIdFromToken("Bearer token")).thenReturn(1);

        R<String> result = cartController.addToCart(ticket, request, response);

        assertEquals("Ticket added to cart", result.getData());
        verify(cartService, times(1)).addToCart(anyString(), eq(ticket));
    }

    @Test
    public void testRemoveFromCart() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        request.setCookies(WebUtils.getCookie(request, "cartId"));

        when(JwtUtils.getIdFromToken("Bearer token")).thenReturn(1);

        R<String> result = cartController.removeFromCart(1, request);

        assertEquals("Ticket removed from cart", result.getData());
        verify(cartService, times(1)).removeFromCart(anyString(), eq(1));
    }

    @Test
    public void testGetCart() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        request.setCookies(WebUtils.getCookie(request, "cartId"));

        when(JwtUtils.getIdFromToken("Bearer token")).thenReturn(1);
        when(cartService.getCart(anyString())).thenReturn(List.of(new Ticket()));

        R<List<Ticket>> result = cartController.getCart(request);

        assertEquals(1, result.getData().size());
        verify(cartService, times(1)).getCart(anyString());
    }
}
