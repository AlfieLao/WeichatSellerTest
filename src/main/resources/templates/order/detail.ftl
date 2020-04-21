<html xmlns="http://www.w3.org/1999/html">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <#--主要内容content-->
    <div id="page-content-wrapper">
        <#--如果没有显示取消按钮加上fliod-->
        <div class="container-fliod"> <#--主要内容content-->
           <div class="row clearfix">
        <#--        单头信息-->
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTo.orderId}</td>
                            <td>${orderDTo.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
        <#--        订单详情-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTo.getOrderDetailList() as item>
                            <tr>
                                <td>${item.orderId}</td>
                                <td>${item.productName}</td>
                                <td>${item.productPrice}</td>
                                <td>${item.productQuantity}</td>
                                <td>${item.productQuantity*item.productPrice}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
        <#--       操作 完结 取消订单-->

                <div class="col-md-12 column">
                    <#if  orderDTo.getOrderStatusEnum().message =="新订单">
                        <a href="/sell/seller/order/finish?orderId=${orderDTo.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTo.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                    </#if>
                </div>
           </div>
            </div>
    </div>
</div>
</body>
</html>

