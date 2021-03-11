package com.lsh.mall.search.service;

import com.lsh.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author Liu Shaohua
 * Create at 2021/3/11
 */
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
