package com.dlnu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 这个接口表示 Tags 实体的 Mapper。
 * 它负责在数据库中执行与标签相关的 CRUD 操作。
 */
@Mapper
public interface TagsMapper {
    /**
     * 根据文章ID删除标签。
     *
     * @param aid 文章ID。
     * @return 受影响的行数。
     */
    int deleteTagsByAid(Long aid);

    /**
     * 保存标签。
     *
     * @param tags 标签数组。
     * @return 受影响的行数。
     */
    int insertTags(@Param("tags") String[] tags);

    /**
     * 根据标签名称获取标签ID列表。
     *
     * @param tagNames 标签名称数组。
     * @return 标签ID列表。
     */
    List<Long> selectTagsIdByTagName(@Param("tagNames") String[] tagNames);

    /**
     * 保存标签与文章的关联关系。
     *
     * @param tagIds 标签ID列表。
     * @param aid    文章ID。
     * @return 受影响的行数。
     */
    int insertTags2ArticleTags(@Param("tagIds") List<Long> tagIds, @Param("aid") Long aid);
}