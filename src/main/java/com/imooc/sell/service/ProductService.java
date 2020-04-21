package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dataTransformObject.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductInfo findOne(String id);

    /** 查询所有在架商品*/
    List<ProductInfo> findAllUp();

    /** 查询所有商品 卖家 分页*/
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 加库存*/
    void increaseStork(List<CartDTO> cartDTOList);

    /** 减库存*/
    void decreaseStork(List<CartDTO> cartDTOList);

    /** 减库存*/
    ProductInfo onSale(String productId);

    /** 减库存*/
    ProductInfo offSale(String productId);

}
