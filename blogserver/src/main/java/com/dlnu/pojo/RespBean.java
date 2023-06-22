package com.dlnu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 响应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RespBean {
    private String status; // 响应状态
    private String msg; // 响应消息
}