package com.demo12306.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo12306.back.entity.Ticket;

public interface TicketService extends IService<Ticket> {
    boolean saveByNumber(Integer trainId,Integer startId,Integer endId,Integer id);

}
