package com.dlnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 这个类是 DataStatisticsComponent，是一个组件类，用于执行每天定时统计PV的任务。
 */
@Component
public class DataStatisticsComponent {
    @Autowired
    ArticleService articleService;

    /**
     * 每天执行一次，统计PV
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void pvStatisticsPerDay() {
        articleService.pvStatisticsPerDay();
    }
}