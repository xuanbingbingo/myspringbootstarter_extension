package com.shuiyou.myspringboot.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestQuartzDemo {

    public static void quartzTest() throws SchedulerException {
        // 1. 创建工厂类 SchedulerFactory
        SchedulerFactory factory = new StdSchedulerFactory();
        // 2. 通过 getScheduler() 方法获得 Scheduler 实例
        Scheduler scheduler = factory.getScheduler();

        // 3. 使用上文定义的 HelloJob
        JobDetail jobDetail = JobBuilder.newJob(QuartzDemo.class)
                //job 的name和group
                .withIdentity("jobName", "jobGroup")
                .build();

        //3秒后启动任务
        Date statTime = new Date(System.currentTimeMillis() + 3000);

        // 4. 启动 Scheduler
        scheduler.start();

        // 5. 创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("jobTriggerName", "jobTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) //两秒执行一次
                .build();

        // 6. 注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);

    }
}
