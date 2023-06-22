package com.dlnu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/**
 * 文章类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
    private Long id; // 文章ID
    private String title; // 文章标题
    private String mdContent; // md文件源码
    private String htmlContent; // html源码
    private String summary; // 文章摘要
    private Long cid; // 文章所属分类ID
    private Long uid; // 文章作者ID
    private Timestamp publishDate; // 文章发布日期
    private Integer state; // 文章状态（0表示草稿箱，1表示已发表，2表示已删除）
    private Integer pageView; // 文章浏览量
    private Timestamp editTime; // 文章编辑时间
    private String[] dynamicTags; // 动态标签数组
    private String nickname; // 作者昵称
    private String cateName; // 分类名称
    private List<Tags> tags; // 文章标签列表
    private String stateStr; // 文章状态字符串表示
}