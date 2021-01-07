package com.lsh.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/7 14:49
 */
@Data
public class MergeVo {
    private Long purchaseId; //整单id
    private List<Long> items;//[1,2,3,4] //合并项集合
}
