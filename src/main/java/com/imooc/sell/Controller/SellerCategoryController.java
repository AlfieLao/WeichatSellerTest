package com.imooc.sell.Controller;


import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.utils.SellException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 卖家类目
 * */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private    CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> list = categoryService.findAll();
        map.put("categoryList",list);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        ProductCategory productCategory;
        if (categoryId !=null){
            productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
//            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory;
        try{
            if (form.getCategoryId()!=null){
                productCategory = categoryService.findOne(form.getCategoryId());
            }else {
                productCategory = new ProductCategory();
            }
            BeanUtils.copyProperties(form,productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","保存成功");
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
