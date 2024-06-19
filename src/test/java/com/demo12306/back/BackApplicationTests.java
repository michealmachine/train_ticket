package com.demo12306.back;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.demo12306.back.dao.RailNodeMapper;
import com.demo12306.back.dao.UserMapper;
import com.demo12306.back.entity.Node;
import com.demo12306.back.entity.RailNode;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.entity.User;
import com.demo12306.back.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo12306.back.service.impl.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class BackApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Autowired
    RailNodeService railNodeService;
    @Autowired
    NodeService nodeService;
    @Autowired
    RailNodeMapper railNodeMapper;
    @Autowired
    TicketService ticketService;
    @Autowired
    RedisTemplate<String,Object> template;
    @Autowired
    CartService cartService;
    @Autowired
    TrainNumberService trainNumberService;
    @Test
    void testCart(){

    }
    @Test
    void testRedis(){
        template.opsForValue().set("1",new Node(1,"test"));
        Node node = (Node) template.opsForValue().get("1");
        System.out.println(node);
    }

    @Test
    void contextLoads() {
        System.out.println(trainNumberService.findTrainsBetweenStations(22, 9));


    }
    @Test
    void MybatisPlusGenerate(){
        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> {
                    builder.author("MadPsycho");
                })
                .packageConfig(builder -> {
                    builder.parent("com.demo12306.back")
                            .entity("entity")
                            .mapper("dao")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("rail_node");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
