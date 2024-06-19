package com.demo12306.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo12306.back.dao.NodeMapper;
import com.demo12306.back.entity.Node;
import com.demo12306.back.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public void testSave(Node node){
        redisTemplate.opsForValue().set(node.getId().toString(),node);
    }
    public Node testGet(Integer id){
        System.out.println(redisTemplate.opsForValue().get(id.toString()));
        return (Node) redisTemplate.opsForValue().get(id.toString());
    }
}
