package com.lsw.search.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "mall_index", type = "goods_type", shards = 1)
//@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    private Long id;

    //标题
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    // 分类
    @Field(type = FieldType.Keyword)
    private String category;

    // 品牌
    @Field(type = FieldType.Keyword)
    private String brand;

    // 价格
    @Field(type = FieldType.Double)
    private Double price;

    // 图片地址
    @Field(index = false, type = FieldType.Keyword)
    private String images;
}
