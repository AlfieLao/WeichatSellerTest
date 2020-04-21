<#--<h1>${orderDTOPage.getTotalPages()}</h1>-->
<#--<h1>${orderDTOPage.totalPages}</h1>-->
<#--<h1>${orderDTOPage.getContent()}</h1>   这里不能2直接这样用 好像只能转化为字符串之类-->

<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <#--主要内容content-->
    <div id="page-content-wrapper">
        <#--如果没有显示取消按钮加上fliod-->
        <div class="container-fliod">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#--table-condensed 紧凑-->
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名1</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付方式</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTo>
                            <tr>
                                <#--                    <tr class="success">-->
                                <td>${orderDTo.orderId}</td>
                                <td>${orderDTo.buyerName}</td>
                                <td>${orderDTo.buyerPhone}</td>
                                <td>${orderDTo.buyerAddress}</td>
                                <td>${orderDTo.orderAmount}</td>
                                <td>${orderDTo.getOrderStatusEnum().message}</td>
                                <td>${orderDTo.getPayStatusEnum().message}</td>
                                <td>${orderDTo.createTime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTo.orderId}"> 详情</a></td>
                                <td >
                                    <#if  orderDTo.getOrderStatusEnum().message =="新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTo.orderId}">取消</a>
                                    </#if>
                                </td>

                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination   pull-right">
                        <#if  currentPage lte 1>
                            <li class="disabled">  <a href="#"> 上一页</a> </li>
                        <#else >
                            <li>  <a href="/sell/seller/order/list?page=${currentPage-1}&size= ${size}">上一页</a></li>
                        </#if>
                        <#--                    遍历循环 0到n-1-->
                        <#--                    <#list 0..<orderDTOPage.getTotalPages() as index>-->
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if  currentPage==index>
                                <li class="disabled">  <a href="#"> ${index}</a> </li>
                            <#else >
                                <li>  <a href="/sell/seller/order/list?page=${index}&size=${size}"> ${index}</a> </li>
                            </#if>
                        <#--                        太多页显示。。。 未实现 用绝对值判断-->
                        <#--                        <#if  currentPage-index lte 2>-->
                        <#--                            <li class="disabled">  <a href="#"> ${index}</a> </li>-->
                        <#--                        <#elseif  currentPage-index gte 4>-->
                        <#--                            <li>  <a href="/sell/seller/order/list?page=${index}&size=${size}"> hh</a> </li>-->
                        <#--                        </#if>-->
                        </#list>
                        <#if  currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled">  <a href="#"> 下一页</a> </li>
                        <#else >
                            <li>  <a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<#--弹窗-->
<div class="modal fade" id="TipsWindow" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看</button>
            </div>
        </div>
    </div>
</div>
<#--播放音乐 循环播放loop  这里必须加上/sell/mp3不然会加到order/list下的相对地址-->
<audio id="notice" loop="loop" src="/sell/mp3/song.mp3">
<#--    sell/static/mp3/song.mp3-->
<#--    <source src="sell/mp3/song.mp3" type="mu">-->
</audio>
<script src="https://cdn.bootcss.com/jquery/3.5.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script>
    var websocket =null;
    if ('WebSocket' in window ){
        websocket = new WebSocket('ws://127.0.0.1:8080/sell/WebSocketTest');
    }else {
        // 弹窗不支持
        alert('该浏览器不支持webSocket!');
    }
     // 刚刚建立连接的事件
    websocket.onopen = function (event) {
        console.log('建立连接');
    }
    websocket.onclose = function (event) {
        console.log('连接关闭');
    }
    websocket.onmessage = function (event) {
        console.log('收到消息:'+event.data);
        // $('#TipsWindow').modal('show');
        $('#TipsWindow').modal();
        // $("notice").get(0).play();
        document.getElementById('notice').play();
        //收到信息 播音乐之类
    }
    websocket.onerror = function (event) {
        console.log('WebSocket通信出错');
    }
    websocket.onbeforeunload = function (event) {
        websocket.close();
        //窗口关闭时关闭websocket
    }
</script>
</body>
</html>

<#-- <#list orderDTOPage.content as orderDTo>-->
<#--  ${orderDTo.orderId}<br>-->
<#--    换行  br-->
<#-- </#list>-->