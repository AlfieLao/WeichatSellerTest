package com.imooc.sell.dataObject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.stream.LongStream;

//如果类目不表名不一致的话，需要下面这种写法
// @Table(name = "S_product_category")
@Entity
@DynamicUpdate(value = true)
@DynamicInsert(value = false)
//@DynamicUpdate //动态更新，如果SQL的时间有设置，在update该数据的时候会自动更新
@Data //自动添加get set toString方法  @Getter只设置get方法 在编译的时候就会生成
public class ProductCategory {

    /**
    * 类目ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //不写这个会报错？？为啥
    private Integer categoryId;

    /**类目名     */
    private String categoryName;

    /**类目编号 */
    private Integer categoryType;

    @Column(updatable = false ,insertable = false)
    private Date createTime;

    @UpdateTimestamp
    private  Date UpdateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    /** 这里需要默认的构造方法，ORM findByID 方法需要*/
    public ProductCategory() {
//        Long sum = LongStream.rangeClosed(0L, 10000000000L).parallel().sum();
    }
}
