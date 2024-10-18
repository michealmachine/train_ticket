package com.demo12306.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.service.TicketService;
import com.demo12306.back.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        when(JwtUtils.getIdFromToken("Bearer token")).thenReturn(1);
        when(ticketService.saveByNumber(1, 1, 1, 1)).thenReturn(true);

        R<String> result = ticketController.save(1, 1, 1, request);

        assertEquals("购买成功", result.getData());
        verify(ticketService, times(1)).saveByNumber(1, 1, 1, 1);
    }

    @Test
    public void testPage() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        when(JwtUtils.getIdFromToken("Bearer token")).thenReturn(1);

        Page<Ticket> ticketPage = new Page<>(1, 10);
        when(ticketService.page(any(Page.class), any())).thenReturn(ticketPage);

        R<Page> result = ticketController.page(1, 10, request);

        assertEquals(ticketPage, result.getData());
        verify(ticketService, times(1)).page(any(Page.class), any());
    }
}
