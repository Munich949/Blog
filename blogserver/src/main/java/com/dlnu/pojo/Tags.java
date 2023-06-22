package com.dlnu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 标签实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tags {
    private Long id; // 标签ID
    private String tagName; // 标签名称
}