package com.imooc.sell.Controller;

import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService pService; /** 提供的都是接口，不是实现*/

    @Autowired
    private CategoryService cService;



    //http://127.0.0.1:8080/sell/buyer/product/list   JsonView插件格式化
    @GetMapping("/list")
    public ResultVO List(){
        //1、查询所有的上架商品
        List< ProductInfo>  productInfos = pService.findAllUp();

        //2、查询类目（一次性查询）
        //  方法一：
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for(ProductInfo info :productInfos) {
//          categoryTypeList.add(info.getCategoryType());
//        }
//        List<ProductCategory> categories = cService.fingByCategoryType(categoryTypeList);
        //  方法二:lambda
        List<Integer> categoryTypeList =   productInfos.stream()
                .map(e ->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> categories = cService.fingByCategoryType(categoryTypeList);
        //3、数据拼装 转化为json格式
        List<ProductVO> data = new ArrayList<>();
        for (ProductCategory productCategory:categories){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfos){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            data.add(productVO);
        }

        ResultVO resultVO = ResultVOUtil.success(data);
//        ResultVO resultVO = ResultVOUtil.success();
        return resultVO;
    }
}
