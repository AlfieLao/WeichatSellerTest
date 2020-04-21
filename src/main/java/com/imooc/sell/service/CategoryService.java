package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductCategory;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> fingByCategoryType(List<Integer> categoryTypes);

    ProductCategory save(ProductCategory productCategory);

}
