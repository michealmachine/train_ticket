package com.demo12306.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo12306.back.dao.RailNodeMapper;
import com.demo12306.back.dao.TrainNumberMetaMapper;
import com.demo12306.back.entity.Node;
import com.demo12306.back.entity.RailNode;
import com.demo12306.back.entity.TrainNumber;
import com.demo12306.back.entity.TrainNumberMeta;
import com.demo12306.back.service.RailNodeService;
import com.demo12306.back.service.TrainNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainNumberServiceImpl implements TrainNumberService {
    @Autowired
    RailNodeService railNodeService;
    @Autowired
    RailNodeMapper railNodeMapper;


    public List<TrainNumber> getAllTrainNumbers() {
        List<RailNode> allRailnode = railNodeMapper.getAllRailnode();
        Map<Integer, List<RailNode>> collect = allRailnode.stream().collect(Collectors.groupingBy(RailNode::getId));
        ArrayList<TrainNumber> trainNumbers = new ArrayList<>();
        for (Map.Entry<Integer, List<RailNode>> entry : collect.entrySet()) {
            TrainNumber trainNumber = new TrainNumber();
            trainNumbers.add(new TrainNumber(entry.getKey(), entry.getValue()));
        }
        return trainNumbers;
    }
    @Cacheable(value = "trainBetweenStations", key = "#start + '-' + #end")
    public List<TrainNumber> findTrainsBetweenStations(Integer start, Integer end) {
        List<TrainNumber> result = new ArrayList<>();
        for (TrainNumber trainNumber : getAllTrainNumbers()) {
            List<RailNode> path = findPath(trainNumber, start, end, new ArrayList<>());
            if (path != null && !path.isEmpty()) {
                result.add(new TrainNumber(trainNumber.getNumber(), path));
            }
        }
        return result;
    }

    private List<RailNode> findPath(TrainNumber trainNumber, Integer current, Integer end, List<RailNode> visited) {
        for (RailNode railNode : trainNumber.getRoutes()) {
            if (visited.contains(railNode)) continue;  // 避免循环
            if (railNode.getStartStationId().equals(current)) {
                if (railNode.getEndStationId().equals(end)) {
                    visited.add(railNode);
                    return visited;
                } else {
                    visited.add(railNode);
                    List<RailNode> newPath = findPath(trainNumber, railNode.getEndStationId(), end, visited);
                    if (newPath != null) {
                        return newPath;
                    } else {
                        visited.remove(railNode);  // 如果路径不通，那么移除这个RailNode
                    }
                }
            }
        }
        return null;
    }

    public Page<TrainNumber> getTrainsNumberPage(int currentPage, int pageSize, Integer id) {
        List<TrainNumber> trainNumbers = getAllTrainNumbers();
        if (id != null) {
            List<TrainNumber> res = trainNumbers.stream().filter(s -> id.equals(s.getNumber())).toList();
            trainNumbers.clear();
            trainNumbers.addAll(res);

        }
        Integer totalNumber = railNodeMapper.getTotalTrainNumbers();
        Page<TrainNumber> Page = new Page<>(currentPage, pageSize, totalNumber);
        Page.setRecords(trainNumbers);
        return Page;
    }
    @CacheEvict(value = "trainBetweenStations", allEntries = true)
    @Override
    public boolean save(List<RailNode> nodes) {
        int insert = 0;
        for (RailNode node : nodes) {
            insert += railNodeMapper.insert(node);
        }
        return insert != 0;
    }

    @Override
    public boolean delete(Integer id) {
        LambdaQueryWrapper<RailNode> qw = new LambdaQueryWrapper<>();
        qw.eq(RailNode::getId, id);
        return railNodeMapper.delete(qw) != 0;
    }


}
