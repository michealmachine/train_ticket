package com.demo12306.back.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @TableId
    private Integer id;
    private Integer trainId;
    private Integer userId;
    private Integer startId;
    private Integer endId;
    private Integer price;

}
