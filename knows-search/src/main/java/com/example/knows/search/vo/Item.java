package com.example.knows.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor     //生成全参构造
@NoArgsConstructor      //生成无参构造
// SpringData的注解
// 指定当前类对应的Es索引名称,SpringData操作数据时会自动创建这个索引
@Document(indexName = "items")
public class Item implements Serializable {
    // SpringData标记主键的注解
    @Id
    private Long id;
    @Field(type = FieldType.Text,
            analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String title; //商品名称
    @Field(type = FieldType.Keyword)
    private String category; //分类
    @Field(type = FieldType.Keyword)
    private String brand;    //品牌
    @Field(type = FieldType.Double)
    private Double price;    //价格
    // index=false表示不会对这个属性的内容创建索引表
    // 优点是节省空间,缺点是不能按照这个列的属性来查询了
    // 而图片地址本来就不会作为查询条件,编写这个属性节省空间
    @Field(type = FieldType.Keyword,index=false)
    private String images;   //图片地址
    //  upload/abc-xxx.png
}
