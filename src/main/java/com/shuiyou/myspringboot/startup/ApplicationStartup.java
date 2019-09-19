package com.shuiyou.myspringboot.startup;

import com.shuiyou.myspringboot.quartz.TestQuartzDemo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

//@Slf4j
public class ApplicationStartup  implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.print("spring容器起来后bean全部加载完成后执行。。。");
        TestQuartzDemo testQuartzDemo = contextRefreshedEvent.getApplicationContext()
                .getBean(TestQuartzDemo.class);
        try {
            testQuartzDemo.quartzTest();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
