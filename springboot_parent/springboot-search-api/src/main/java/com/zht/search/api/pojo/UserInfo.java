package com.zht.search.api.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 不写 Field 的时候，会自动添加域
 * indexName：对应索引名称
 *
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "userinfo", type = "docs")
public class UserInfo implements Serializable {

    /**
     * id 人员编号
     */
    @Id
    private int id;

    /**
     * 姓名
     * index: 是否使用分词
     * store: 是否存储
     * searchAnalyzer: 搜索时使用的分词器、
     * analyzer： 创建索引的分词器
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart",index = true, store = false)
    private String name;

    /**
     * 年龄
     */
    @Field(type = FieldType.Long)
    private long age;

    /**
     * 城市
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart",index = true, store = false, fielddata = true)
    private String city;

    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart",index = true, store = false)
    private String description;


}
