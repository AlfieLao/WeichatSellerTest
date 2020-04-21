package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> fingByCategoryType(List<Integer> categoryTypes) {
        return repository.findByCategoryTypeIn(categoryTypes);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
