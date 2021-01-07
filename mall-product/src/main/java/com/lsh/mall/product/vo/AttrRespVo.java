package com.lsh.mall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2021/1/2 23:41
 */
@Data
public class AttrRespVo extends AttrVo{

    private String catelogName;
    private String groupName;

    private List<Long> catelogPath;
}
