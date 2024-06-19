package com.demo12306.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo12306.back.dao.TicketMapper;
import com.demo12306.back.dao.TrainNumberMetaMapper;
import com.demo12306.back.dao.UserMapper;
import com.demo12306.back.entity.*;
import com.demo12306.back.service.TicketService;
import com.demo12306.back.service.TrainNumberMetaService;
import com.demo12306.back.service.TrainNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TrainNumberService trainNumberService;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    TrainNumberMetaMapper trainNumberMetaMapper;
    @Autowired
    TrainNumberMetaService trainNumberMetaService;
    @Override
    public boolean saveByNumber(Integer trainId, Integer startId, Integer endId, Integer id) {
        User user = userMapper.selectById(id);
        List<TrainNumber> allTrainNumbers = trainNumberService.getAllTrainNumbers();
        Integer price = allTrainNumbers.stream().filter(trainNumber -> trainNumber.getNumber().equals(trainId))
                .findFirst()
                .map(tn -> {
                    List<RailNode> routes = tn.getRoutes();
                    int startIndex = IntStream.range(0, routes.size())
                            .filter(i -> routes.get(i).getStartStationId().equals(startId))
                            .findFirst()
                            .orElse(-1);
                    int endIndex = IntStream.range(0, routes.size())
                            .filter(i -> routes.get(i).getEndStationId().equals(endId))
                            .findFirst()
                            .orElse(-1);
                    if (startIndex != -1 && endIndex != -1 && startIndex <= endIndex) {
                        return routes.subList(startIndex, endIndex + 1).stream()
                                .mapToInt(RailNode::getPrice)
                                .sum();
                    }
                    return 0;
                }).orElse(0);
        LambdaUpdateWrapper<TrainNumberMeta> qw = new LambdaUpdateWrapper<>();
        qw.setSql("available_seats=available_seats-1").in(TrainNumberMeta::getId,trainId);
        trainNumberMetaService.update(qw);
        return ticketMapper.insert(new Ticket(null,trainId,user.getId(),startId,endId,price))!=0;
    }
}
