package com.dlnu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * 分类类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Long id; // 分类ID
    private String cateName; // 分类名称
    private Timestamp date; // 分类创建日期
}