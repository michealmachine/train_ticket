package com.demo12306.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.*;
import com.demo12306.back.entity.RailNode;
import com.demo12306.back.entity.TrainNumber;
import com.demo12306.back.service.TrainNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TrainNumberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainNumberService trainNumberService;

    @InjectMocks
    private TrainNumberController trainNumberController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainNumberController).build();
    }

    @Test
    public void testSave() throws Exception {
        List<RailNode> nodes = Arrays.asList(new RailNode(), new RailNode());
        when(trainNumberService.save(any())).thenReturn(true);

        mockMvc.perform(post("/number")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"id\":1,\"startStationId\":1,\"endStationId\":2,\"price\":100},{\"id\":2,\"startStationId\":2,\"endStationId\":3,\"price\":150}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("添加成功"));

        verify(trainNumberService, times(1)).save(any());
    }

    @Test
    public void testPage() throws Exception {
        Page<TrainNumber> page = new Page<>();
        when(trainNumberService.getTrainsNumberPage(anyInt(), anyInt(), any())).thenReturn(page);

        mockMvc.perform(get("/number/page")
                .param("page", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));

        verify(trainNumberService, times(1)).getTrainsNumberPage(anyInt(), anyInt(), any());
    }

    @Test
    public void testFind() throws Exception {
        List<TrainNumber> trainNumbers = Arrays.asList(new TrainNumber(), new TrainNumber());
        when(trainNumberService.findTrainsBetweenStations(anyInt(), anyInt())).thenReturn(trainNumbers);

        mockMvc.perform(get("/number")
                .param("start", "1")
                .param("end", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));

        verify(trainNumberService, times(1)).findTrainsBetweenStations(anyInt(), anyInt());
    }

    @Test
    public void testDelete() throws Exception {
        when(trainNumberService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/number/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("删除成功"));

        verify(trainNumberService, times(1)).delete(anyInt());
    }
}
