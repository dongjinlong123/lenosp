package com.len.core.quartz.CustomQuartz;

import com.len.core.annotation.Log;
import com.len.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 短信发送任务
 */
@Slf4j
public class SmsSendSchdule implements Job{

  @Autowired
  private SmsService smsService;
  @Log(desc = "添加用户", type = Log.LOG_TYPE.EMAIL)
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("SmsSendSchdule：启动任务=======================");
    run();
    log.info("SmsSendSchdule：下次执行时间====="+
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            .format(context.getNextFireTime())+"==============");
  }

  public void run(){
    smsService.sendMail("316799047@qq.com","测试邮件","测试");
    smsService.sendMail("316799047@qq.com","测试邮件2","测试内容",new File("F:\\头像.png"),"#",
            new File[]{new File("F:\\djl\\ecjtu_info.xlsx")
    ,new File("F:\\djl\\wechat.log")});
   log.info("SmsSendSchdule：执行完毕=======================");

  }
}
