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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainNumberMetaControllerTest {

    @Mock
    private TrainNumberMetaService trainNumberMetaService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TrainNumberMetaController trainNumberMetaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPage() {
        Page<TrainNumberMeta> page = new Page<>(1, 10);
        when(trainNumberMetaService.page(any(Page.class), any())).thenReturn(page);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<Page> responseEntity = trainNumberMetaController.page(1, 10, null);

        assertEquals(1, responseEntity.getCode());
        assertEquals(page, responseEntity.getData());
    }

    @Test
    public void testSave() {
        TrainNumberMeta trainNumberMeta = new TrainNumberMeta();
        when(trainNumberMetaService.save(any(TrainNumberMeta.class))).thenReturn(true);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<String> responseEntity = trainNumberMetaController.save(trainNumberMeta);

        assertEquals(1, responseEntity.getCode());
        assertEquals("添加成功", responseEntity.getData());
    }

    @Test
    public void testDelete() {
        when(trainNumberMetaService.remove(any())).thenReturn(true);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<String> responseEntity = trainNumberMetaController.delete(1);

        assertEquals(1, responseEntity.getCode());
        assertEquals("删除成功", responseEntity.getData());
    }

    @Test
    public void testAllTickes() {
        Page<Ticket> page = new Page<>(1, 10);
        when(ticketService.page(any(Page.class))).thenReturn(page);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<Page> responseEntity = trainNumberMetaController.page(1, 10);

        assertEquals(1, responseEntity.getCode());
        assertEquals(page, responseEntity.getData());
    }
}
