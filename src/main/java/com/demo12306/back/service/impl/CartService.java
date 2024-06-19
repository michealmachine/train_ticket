package com.demo12306.back.service.impl;

import com.demo12306.back.entity.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void addToCart(String cartId, Ticket ticket) {
        // key 设为 cartId
        List<Object> cart = redisTemplate.opsForList().range(cartId, 0, -1);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(ticket);
        redisTemplate.delete(cartId);
        redisTemplate.opsForList().rightPushAll(cartId, cart);
    }

    public List<Ticket> getCart(String cartId) {
        List<Object> cart = redisTemplate.opsForList().range(cartId, 0, -1);
        if (cart == null) {
            return new ArrayList<>();
        }
        // 类型转换
        List<Ticket> ticketList = new ArrayList<>();
        for (Object obj : cart) {
            if (obj instanceof Ticket) {
                ticketList.add((Ticket) obj);
            }
        }
        return ticketList;
    }

    public void removeFromCart(String cartId, Integer ticketId) {
        List<Object> cart = redisTemplate.opsForList().range(cartId, 0, -1);
        if (cart != null) {
            cart.removeIf(ticket -> ticket instanceof Ticket && ((Ticket) ticket).getId().equals(ticketId));
            // 重新存入Redis
            redisTemplate.delete(cartId);
            if(!cart.isEmpty())redisTemplate.opsForList().rightPushAll(cartId, cart);
        }
    }

    public void clearCart(String cartId) {
        redisTemplate.delete(cartId);
    }
}
