package com.demo12306.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.entity.TrainNumberMeta;
import com.demo12306.back.service.TicketService;
import com.demo12306.back.service.TrainNumberMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tSet")
public class TrainNumberMetaController {
    @Autowired
    TrainNumberMetaService trainNumberMetaService;
    @Autowired
    TicketService ticketService;
    //查询车次剩余车票量
    @GetMapping("/page")
    public R<Page> page(@RequestParam Integer page,@RequestParam Integer pageSize,@RequestParam(required = false)Integer id){
        Page<TrainNumberMeta> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<TrainNumberMeta> qw = new LambdaQueryWrapper<>();
        if(id!=null){
            qw.eq(TrainNumberMeta::getId,id);
        }
        trainNumberMetaService.page(pageInfo,qw);
        return R.success(pageInfo);
    }
    //添加车次车票量
    @PostMapping
    public R<String> save(@RequestBody TrainNumberMeta trainNumberMeta){
        if(trainNumberMetaService.save(trainNumberMeta)){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }
    //删除某车次的车票
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Integer id){
        LambdaQueryWrapper<TrainNumberMeta> qw = new LambdaQueryWrapper<>();
        qw.eq(TrainNumberMeta::getId,id);
        if(trainNumberMetaService.remove(qw))return R.success("删除成功");
        return R.error("删除失败");
    }
    //查看所有用户的订单
    @GetMapping("/allTickes")
    public R<Page> page(@RequestParam Integer page,@RequestParam Integer pageSize){
        Page<Ticket> ticketPage = new Page<>(page,pageSize);
        return R.success(ticketService.page(ticketPage));
    }


}
