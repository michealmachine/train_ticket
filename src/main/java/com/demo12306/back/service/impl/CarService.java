package com.demo12306.back.service.impl;

import com.demo12306.back.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CarService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public void addCart(String cardId, CartItem item){
        redisTemplate.opsForHash().put(cardId,item.getTrainId().toString(),item);
    }
    public Map<Object, Object> getCart(String cartId) {
        return redisTemplate.opsForHash().entries(cartId);
    }

    public void removeFromCart(String cartId, Integer trainId) {
        redisTemplate.opsForHash().delete(cartId, trainId.toString());
    }
}
