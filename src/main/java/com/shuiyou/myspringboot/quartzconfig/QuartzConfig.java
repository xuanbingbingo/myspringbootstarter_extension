package com.shuiyou.myspringboot.quartzconfig;

import com.shuiyou.myspringboot.quartz.QuartzDemo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Quartz配置类
 */
@Configuration
public class QuartzConfig {
    /**
     * 1。创建Job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        //关联我们自己的Job类
        factoryBean.setJobClass(QuartzDemo.class);
        return factoryBean;
    }

    /**
     * 2。创建Trigger对象
     * 一个简单的Trigger
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        // 关联JobDetail对象
        factoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        // 设置触发时间
        factoryBean.setRepeatInterval(2000);
        // 设置重复次数
        factoryBean.setRepeatCount(5);
        return factoryBean;
    }

    /**
     * Cron Trigger
     * 相对复杂一些的Trigger
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        // 设置触发时间。5秒执行一次
        triggerFactoryBean.setCronExpression("0/5 * * * * ?");
        return triggerFactoryBean;
    }

    /**
     * 3。创建Scheduler对象
     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean){
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        /// 关联Trigger
//        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
//
//        return schedulerFactoryBean;
//    }

    // 使用Cron Trigger，上面是使用简单Trigger
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        /// 关联Trigger
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean.getObject());
        schedulerFactoryBean.setJobFactory(myAdaptableJobFactory);
        return schedulerFactoryBean;
    }
}
