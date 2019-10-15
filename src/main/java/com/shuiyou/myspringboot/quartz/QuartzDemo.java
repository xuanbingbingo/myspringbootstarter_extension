package com.shuiyou.myspringboot.quartz;

import com.shuiyou.myspringboot.quartzconfig.MyAdaptableJobFactory;
import com.shuiyou.myspringboot.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Job类
 */
@Component
public class QuartzDemo implements Job {
//     先在Job类中注入业务对象
    @Autowired
    UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("Excute...Job被触发");
        this.userService.printTest();
    }
}
