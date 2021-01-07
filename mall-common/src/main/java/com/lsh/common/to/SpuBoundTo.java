package com.lsh.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/6 0:05
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
