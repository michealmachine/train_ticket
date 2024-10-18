package com.demo12306.back.controller;

import com.demo12306.back.common.R;
import com.demo12306.back.entity.*;
import com.demo12306.back.service.NodeService;
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

public class NodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NodeService nodeService;

    @InjectMocks
    private NodeController nodeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(nodeController).build();
    }

    @Test
    public void testSave() throws Exception {
        Node node = new Node(1, "Test Node");
        when(nodeService.getOne(any())).thenReturn(null);
        when(nodeService.save(any(Node.class))).thenReturn(true);

        mockMvc.perform(post("/node")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Node\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("添加成功"));

        verify(nodeService, times(1)).save(any(Node.class));
    }

    @Test
    public void testSelect() throws Exception {
        List<Node> nodes = Arrays.asList(new Node(1, "Node1"), new Node(2, "Node2"));
        when(nodeService.list()).thenReturn(nodes);

        mockMvc.perform(get("/node"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Node1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Node2"));

        verify(nodeService, times(1)).list();
    }

    @Test
    public void testDrop() throws Exception {
        when(nodeService.remove(any())).thenReturn(true);

        mockMvc.perform(delete("/node/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("删除成功"));

        verify(nodeService, times(1)).remove(any());
    }
}
