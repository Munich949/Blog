package com.dlnu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 角色实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    private Long id; // 角色ID
    private String name; // 角色名称
}