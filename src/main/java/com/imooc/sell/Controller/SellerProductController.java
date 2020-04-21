package com.imooc.sell.Controller;

import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.KeyUtil;
import com.imooc.sell.utils.SellException;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/** 卖家端商品*/
@RequestMapping("/seller/product")
@Controller
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        //page 从0开始
        Page<ProductInfo>  productInfoPage = productService.findAll(PageRequest.of(page - 1, size));
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /** 商品上架*/
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId")String productId,
                               Map<String, Object> map) {
//        ProductInfo productInfo =
      try{
         productService.onSale(productId);
      }  catch (SellException e) {
          map.put("msg",e.getMessage());
          map.put("url","/sell/seller/product/list");
          return new ModelAndView("common/error",map);
      }
      map.put("url","/sell/seller/product/list");
      return new ModelAndView("common/success",map);
    }

    /** 商品下架*/
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId")String productId,
                               Map<String, Object> map) {
//        ProductInfo productInfo =
        try{
            productService.offSale(productId);
        }  catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /** 新增修改商品*/
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,
                                Map<String, Object> map) {

        try{
            if(!StringUtils.isEmpty(productId)){
                ProductInfo productInfo = productService.findOne(productId);
                map.put("productInfo",productInfo);
            }
            List<ProductCategory> list = categoryService.findAll();
            map.put("categoryList",list);
            return new ModelAndView("product/index",map);
        }  catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
    }

    /**
     * 保存更新
     *
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors())
        {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        try{
            ProductInfo productInfo;
            if (StringUtils.isEmpty(productForm.getProductId()))
            {
                productInfo = new ProductInfo();
                productInfo.setProductId(KeyUtil.genUniqueKey());
            }else {
                productInfo = productService.findOne(productForm.getProductId());
            }

            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","保存成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
