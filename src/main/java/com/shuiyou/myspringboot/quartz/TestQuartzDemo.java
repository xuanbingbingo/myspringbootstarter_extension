package com.shuiyou.myspringboot.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TestQuartzDemo {

    @Resource(name = "Scheduler")
    private Scheduler scheduler;
    public void quartzTest() throws SchedulerException {
//        // 1. 创建工厂类 SchedulerFactory
//        SchedulerFactory factory = new StdSchedulerFactory();
//        // 2. 通过 getScheduler() 方法获得 Scheduler 实例
//        Scheduler scheduler = factory.getScheduler();

        // 3.以下是定义一个job和trigger
        //  使用上文定义的Job创建Job
        JobDetail jobDetail = JobBuilder.newJob(QuartzDemo.class)
                //job 的name和group
                .withIdentity("jobName", "jobGroup")
                .build();

        // 3.1创建Trigger
        // 使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("jobTriggerName", "jobTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) //五秒执行一次
                .build();

        // 3.2注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);

        // 然后可以再定义一个job和trigger（重复添加过程3,此处省略）

        // 4.启动 Scheduler
        scheduler.start();

    }
}
