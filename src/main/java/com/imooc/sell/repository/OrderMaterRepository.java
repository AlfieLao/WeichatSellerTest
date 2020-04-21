package com.imooc.sell.repository;

import com.imooc.sell.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMaterRepository extends JpaRepository<OrderMaster,String> {

    /** 分页 根据买家OpenID来查*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
