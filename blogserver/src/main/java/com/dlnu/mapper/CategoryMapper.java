package com.dlnu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dlnu.pojo.Category;

import java.util.List;

/**
 * 这个接口表示 Category 实体的 Mapper。
 * 它负责在数据库中执行对 Category 表的 CRUD 操作。
 */
@Mapper
public interface CategoryMapper {
    /**
     * 从数据库中获取所有的分类。
     *
     * @return 所有分类的列表。
     */
    List<Category> getAllCategories();

    /**
     * 根据分类的 ID 删除分类。
     *
     * @param ids 要删除的分类的 ID 数组。
     * @return 受影响的行数。
     */
    int deleteCategoryByIds(@Param("ids") String[] ids);

    /**
     * 更新数据库中的一个分类。
     *
     * @param category 要更新的分类对象。
     * @return 受影响的行数。
     */
    int updateCategoryById(Category category);

    /**
     * 向数据库中添加一个新的分类。
     *
     * @param category 要添加的分类对象。
     * @return 受影响的行数。
     */
    int addCategory(Category category);
}