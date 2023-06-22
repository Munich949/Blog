package com.dlnu.service;

import com.dlnu.mapper.ArticleMapper;
import com.dlnu.mapper.TagsMapper;
import com.dlnu.pojo.Article;
import com.dlnu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 这个类是 ArticleService，是一个服务类，用于处理与文章相关的业务逻辑。
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagsMapper tagsMapper;

    /**
     * 添加新的文章
     *
     * @param article 要添加的文章对象
     * @return 受影响的行数
     */
    public int addNewArticle(Article article) {
        // 处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            // 直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId() == -1) {
            // 添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                // 设置发表日期
                article.setPublishDate(timestamp);
            }
            article.setEditTime(timestamp);
            // 设置当前用户
            article.setUid(Util.getCurrentUser().getId());
            int i = articleMapper.addNewArticle(article);
            // 打标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                // 设置发表日期
                article.setPublishDate(timestamp);
            }
            // 更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleMapper.updateArticle(article);
            // 修改标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        }
    }

    /**
     * 给文章添加标签
     *
     * @param dynamicTags 标签数组
     * @param aid         文章ID
     * @return 受影响的行数
     */
    private int addTagsToArticle(String[] dynamicTags, Long aid) {
        // 1. 删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        // 2. 将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        // 3. 查询这些标签的id
        List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        // 4. 重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }

    /**
     * 去除文章内容中的 HTML 标签，返回纯文本内容
     *
     * @param content 文章内容
     * @return 去除 HTML 标签后的纯文本内容
     */
    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    /**
     * 根据文章状态、页码、每页数量和关键词获取文章列表
     *
     * @param state    文章状态
     * @param page     页码
     * @param count    每页数量
     * @param keywords 关键词
     * @return 符合条件的文章列表
     */
    public List<Article> getArticleByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = Util.getCurrentUser().getId();
        return articleMapper.getArticleByState(state, start, count, uid, keywords);
    }

    /**
     * 根据文章状态、用户ID和关键词统计文章数量
     *
     * @param state    文章状态
     * @param uid      用户ID
     * @param keywords 关键词
     * @return 符合条件的文章数量
     */
    public int getArticleCountByState(Integer state, Long uid, String keywords) {
        return articleMapper.getArticleCountByState(state, uid, keywords);
    }

    /**
     * 更新文章状态
     *
     * @param aids  文章ID数组
     * @param state 新的状态
     * @return 受影响的行数
     */
    public int updateArticleState(Long[] aids, Integer state) {
        if (state == 2) {
            return articleMapper.deleteArticleById(aids);
        } else {
            return articleMapper.updateArticleState(aids, 2); // 将文章放入回收站
        }
    }

    /**
     * 从回收站还原文章
     *
     * @param articleId 文章ID
     * @return 受影响的行数
     */
    public int restoreArticle(Integer articleId) {
        return articleMapper.updateArticleStateById(articleId, 1); // 从回收站还原文章
    }

    /**
     * 根据文章ID获取文章详情，并增加文章的浏览量
     *
     * @param aid 文章ID
     * @return 文章对象
     */
    public Article getArticleById(Long aid) {
        Article article = articleMapper.getArticleById(aid);
        articleMapper.pvIncrement(aid); // 文章的浏览量加1
        return article;
    }

    /**
     * 统计每天的文章浏览量
     */
    public void pvStatisticsPerDay() {
        articleMapper.pvStatisticsPerDay();
    }

    /**
     * 获取最近七天的日期
     *
     * @return 日期列表
     */
    public List<String> getCategories() {
        return articleMapper.getCategories(Util.getCurrentUser().getId());
    }

    /**
     * 获取最近七天的数据统计
     *
     * @return 数据统计列表
     */
    public List<Integer> getDataStatistics() {
        return articleMapper.getDataStatistics(Util.getCurrentUser().getId());
    }
}