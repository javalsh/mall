package com.lsh.mall.ware.vo;

import lombok.Data;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/7 14:59
 */
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
