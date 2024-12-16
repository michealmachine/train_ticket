package com.demo12306.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.*;
import com.demo12306.back.entity.TrainNumberMeta;
import com.demo12306.back.service.TrainNumberMetaService;
import com.demo12306.back.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainNumberMetaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainNumberMetaService trainNumberMetaService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TrainNumberMetaController trainNumberMetaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainNumberMetaController).build();
    }

    @Test
    public void testPage() {
        Page<TrainNumberMeta> page = new Page<>(1, 10);
        when(trainNumberMetaService.page(any(Page.class), any())).thenReturn(page);

        R<Page> response = trainNumberMetaController.page(1, 10, null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(page, response.getData());
    }

    @Test
    public void testSave() {
        TrainNumberMeta trainNumberMeta = new TrainNumberMeta();
        when(trainNumberMetaService.save(any(TrainNumberMeta.class))).thenReturn(true);

        R<String> response = trainNumberMetaController.save(trainNumberMeta);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("添加成功", response.getData());
    }

    @Test
    public void testDelete() {
        when(trainNumberMetaService.remove(any())).thenReturn(true);

        R<String> response = trainNumberMetaController.delete(1);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("删除成功", response.getData());
    }

    @Test
    public void testAllTickes() {
        Page<Ticket> page = new Page<>(1, 10);
        when(ticketService.page(any(Page.class))).thenReturn(page);

        R<Page> response = trainNumberMetaController.page(1, 10);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(page, response.getData());
    }
}
