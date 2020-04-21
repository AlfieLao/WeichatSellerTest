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
        <#--如果没有显示取消按钮加上fluid-->
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.productIcon)!''}" height="200" width="200" alt="">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <#--注意这里是select 数据库的数据不是input            -->
                            <select name="categoryType" type="number" class="form-control" >
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#-- 如果类目存在并且与数据库存在该类目，那么就要选择这个选项 -->
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType==category.categoryType>
                                        selected
                                    </#if>>
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>

