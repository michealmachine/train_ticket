package com.demo12306.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.common.R;
import com.demo12306.back.entity.Ticket;
import com.demo12306.back.service.TicketService;
import com.demo12306.back.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping
    public R<String> save(@RequestParam Integer trainId, @RequestParam Integer startId, @RequestParam Integer endId, HttpServletRequest request){
        Integer id = JwtUtils.getIdFromToken(request.getHeader("Authorization"));
        if(ticketService.saveByNumber(trainId,startId,endId,id))return R.success("购买成功");
        return R.error("购买失败");
    }
    @GetMapping("/page")
    public R<Page> page(@RequestParam Integer page,@RequestParam Integer pageSize,HttpServletRequest request){
        Integer id = JwtUtils.getIdFromToken(request.getHeader("Authorization"));
        LambdaQueryWrapper<Ticket> lq = new LambdaQueryWrapper<>();
        lq.eq(Ticket::getUserId,id);
        Page<Ticket> ticketPage = new Page<>(page,pageSize);
        return R.success(ticketService.page(ticketPage,lq));
    }
}
