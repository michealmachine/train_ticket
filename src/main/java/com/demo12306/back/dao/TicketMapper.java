package com.demo12306.back.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo12306.back.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {
}
