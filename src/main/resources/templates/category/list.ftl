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
                            <th>类目id</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list categoryList as category>
                            <tr>
                                <td>${category.categoryId}</td>
                                <td>${category.categoryName}</td>
                                <td>${category.categoryType}</td>
                                <td>${category.createTime}</td>
                                <td>${category.updateTime}</td>
                                <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                             </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>

<#-- <#list orderDTOPage.content as orderDTo>-->
<#--  ${orderDTo.orderId}<br>-->
<#--    换行  br-->
<#-- </#list>-->