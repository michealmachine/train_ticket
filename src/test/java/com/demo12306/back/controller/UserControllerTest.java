package com.demo12306.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.User;
import com.demo12306.back.service.UserService;
import com.demo12306.back.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<Object> responseEntity = userController.login(user);

        assertEquals(1, responseEntity.getCode());
        assertEquals("testUser", ((Map<String, Object>) responseEntity.getData()).get("username"));
        verify(userService, times(1)).getOne(any());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<String> responseEntity = userController.save(user);

        assertEquals(1, responseEntity.getCode());
        assertEquals("添加成功", responseEntity.getData());
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testStatus() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<String> responseEntity = userController.status(ids);

        assertEquals(1, responseEntity.getCode());
        assertEquals("修改成功", responseEntity.getData());
        verify(userService, times(1)).update(any());
    }

    @Test
    public void testPage() {
        Page<User> page = new Page<>(1, 10);
        when(userService.page(any(), any())).thenReturn(page);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        R<Page> responseEntity = userController.page(1, 10, "testUser");

        assertEquals(1, responseEntity.getCode());
        assertEquals(page, responseEntity.getData());
        verify(userService, times(1)).page(any(), any());
    }
}
