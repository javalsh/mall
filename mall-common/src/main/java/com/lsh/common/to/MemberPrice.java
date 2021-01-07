package com.lsh.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/6 0:06
 */
@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;
}
