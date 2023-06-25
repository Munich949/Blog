package com.dlnu.mapper;

import com.dlnu.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 这个接口表示 Article 实体的 Mapper。
 * 它负责在数据库中执行与文章相关的 CRUD 操作。
 */
@Mapper
public interface ArticleMapper {
    /**
     * 向数据库插入一篇新的文章。
     *
     * @param article 要插入的文章对象。
     * @return 受影响的行数。
     */
    int insertNewArticle(Article article);

    /**
     * 更新数据库中的一篇文章。
     *
     * @param article 要更新的文章对象。
     * @return 受影响的行数。
     */
    int updateArticle(Article article);

    /**
     * 根据状态、起始索引、数量、用户ID和关键词检索文章列表。
     *
     * @param state    要检索的文章的状态。
     * @param start    起始索引。
     * @param count    最大数量。
     * @param uid      用户ID。
     * @param keywords 关键词。
     * @return 符合指定条件的文章列表。
     */
    List<Article> selectArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

    /**
     * 根据状态、用户ID和关键词统计文章数量。
     *
     * @param state    要统计的文章的状态。
     * @param uid      用户ID。
     * @param keywords 关键词。
     * @return 符合指定条件的文章数量。
     */
    int selectArticleCountByState(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    /**
     * 更新多篇文章的状态。
     *
     * @param aids  要更新的文章的ID数组。
     * @param state 新的状态。
     * @return 受影响的行数。
     */
    int updateArticleState(@Param("aids") Long[] aids, @Param("state") Integer state);

    /**
     * 根据文章ID更新文章的状态。
     *
     * @param articleId 文章ID。
     * @param state     新的状态。
     * @return 受影响的行数。
     */
    int updateArticleStateById(@Param("articleId") Integer articleId, @Param("state") Integer state);

    /**
     * 根据文章ID删除多篇文章。
     *
     * @param aids 要删除的文章的ID数组。
     * @return 受影响的行数。
     */
    int deleteArticleById(@Param("aids") Long[] aids);

    /**
     * 根据文章ID检索文章。
     *
     * @param aid 文章ID。
     * @return 符合指定ID的文章对象。
     */
    Article selectArticleById(Long aid);

    /**
     * 文章的浏览量加1。
     *
     * @param aid 文章ID。
     */
    void pvIncrement(Long aid);

    /**
     * 每天统计文章的浏览量。
     */
    void pvStatisticsPerDay();

    /**
     * 根据用户ID获取分类列表。
     *
     * @param uid 用户ID。
     * @return 分类列表。
     */
    List<String> selectCategories(Long uid);

    /**
     * 根据用户ID获取数据统计。
     *
     * @param uid 用户ID。
     * @return 数据统计列表。
     */
    List<Integer> selectDataStatistics(Long uid);
}