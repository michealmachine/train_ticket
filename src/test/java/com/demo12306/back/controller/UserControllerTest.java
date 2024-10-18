package com.demo12306.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.*;
import com.demo12306.back.entity.User;
import com.demo12306.back.service.UserService;
import com.demo12306.back.utils.JwtUtils;
import com.demo12306.back.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        User userFromDb = new User();
        userFromDb.setUsername("testUser");
        userFromDb.setPassword("testPassword");
        userFromDb.setStatus(1);
        userFromDb.setId(1);
        userFromDb.setMinister(false);

        when(userService.getOne(any())).thenReturn(userFromDb);

        R<Object> response = userController.login(user);

        assertEquals(1, response.getCode());
        assertEquals("testUser", ((Map<String, Object>) response.getData()).get("username"));
        verify(userService, times(1)).getOne(any());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        R<String> response = userController.save(user);

        assertEquals(1, response.getCode());
        assertEquals("添加成功", response.getData());
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testStatus() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        R<String> response = userController.status(ids);

        assertEquals(1, response.getCode());
        assertEquals("修改成功", response.getData());
        verify(userService, times(1)).update(any());
    }

    @Test
    public void testPage() {
        Page<User> page = new Page<>(1, 10);
        when(userService.page(any(), any())).thenReturn(page);

        R<Page> response = userController.page(1, 10, "testUser");

        assertEquals(1, response.getCode());
        assertEquals(page, response.getData());
        verify(userService, times(1)).page(any(), any());
    }
}
