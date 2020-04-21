package com.imooc.sell.repository;

import com.imooc.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String > {

    List<ProductInfo> findByProductStatus(Integer status);//根据商品状态查找


}
