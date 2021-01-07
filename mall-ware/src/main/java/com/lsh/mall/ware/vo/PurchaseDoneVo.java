package com.lsh.mall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/7 14:58
 */
@Data
public class PurchaseDoneVo {

    //采购单id
    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
