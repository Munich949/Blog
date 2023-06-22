package com.dlnu.controller;

import com.dlnu.pojo.RespBean;
import com.dlnu.pojo.Category;
import com.dlnu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 超级管理员专属Controller
 * getAllCategories方法用于获取所有分类列表。
 * deleteById方法根据分类ID删除分类。
 * addNewCate方法用于添加新分类。
 * updateCate方法用于更新分类。
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 获取所有分类列表
     * @return 分类列表
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * 根据分类ID删除分类
     * @param ids 分类ID
     * @return 响应实体类
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    /**
     * 添加新分类
     * @param category 分类对象
     * @return 响应实体类
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewCate(Category category) {
        if ("".equals(category.getCateName()) || category.getCateName() == null) {
            return new RespBean("error", "请输入栏目名称!");
        }

        int result = categoryService.addCategory(category);

        if (result == 1) {
            return new RespBean("success", "添加成功!");
        }
        return new RespBean("error", "添加失败!");
    }

    /**
     * 更新分类
     * @param category 分类对象
     * @return 响应实体类
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Category category) {
        int i = categoryService.updateCategoryById(category);
        if (i == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}