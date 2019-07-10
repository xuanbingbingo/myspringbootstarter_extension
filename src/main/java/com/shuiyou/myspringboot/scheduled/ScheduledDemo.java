package com.shuiyou.myspringboot.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Scheduled定时任务器(此例子可以不用，使用quartz即可)
 */
@Component
public class ScheduledDemo {

    /**
     * 一个定时任务方法
     * @Scheduled: 设置定时任务的
     * cron属性：cron表达式。定时任务触发时间的一个字符串的表达形式
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduledMethod(){
//        System.out.println("定时器被触发"+ new Date());
    }
}
