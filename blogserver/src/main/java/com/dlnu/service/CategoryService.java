package com.dlnu.service;

import com.dlnu.mapper.CategoryMapper;
import com.dlnu.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 这个类是 CategoryService，是一个服务类，用于处理与分类相关的业务逻辑。
 */
@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     *
     * @return 所有分类的列表
     */
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    /**
     * 根据分类ID删除分类
     *
     * @param ids 要删除的分类的ID字符串
     * @return 是否成功删除所有分类
     */
    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryMapper.deleteCategoryByIds(split);
        return result == split.length;
    }

    /**
     * 根据分类ID更新分类
     *
     * @param category 要更新的分类对象
     * @return 受影响的行数
     */
    public int updateCategoryById(Category category) {
        return categoryMapper.updateCategoryById(category);
    }

    /**
     * 添加新的分类
     *
     * @param category 要添加的分类对象
     * @return 受影响的行数
     */
    public int addCategory(Category category) {
        category.setDate(new Timestamp(System.currentTimeMillis()));
        return categoryMapper.addCategory(category);
    }
}