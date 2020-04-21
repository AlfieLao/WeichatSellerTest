package com.imooc.sell.repository;

import com.imooc.sell.dataObject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    /** 根据订单ID来查*/
    List<OrderDetail> findByOrderId(String orderID);
}
