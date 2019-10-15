package com.len.common;

import com.len.service.WxMenuService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 获取微信accessToken 的定时任务，同时更新菜单信息
 * @author 90411
 *
 */
@Slf4j
@Component
public class WeixinAccessTokenTask{


	@Autowired
	private WxMessageUtil wxMessageUtil;
	@Autowired
	private WxMenuService wxMenuService;
	
	// 第一次延迟1秒执行，当执行完后7100秒再执行
	@Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000)
	public void execute() {
		System.out.println("getWeixinAccessToken：启动任务=======================");
		run();
	}
	public void run(){
		getWeixinAccessToken();
		System.out.println("getWeixinAccessToken：执行完毕=======================");

	}
	public void getWeixinAccessToken() {

		try {
			wxMessageUtil.getXCXAccessToken();//小程序的token

			//获取accessToken
			wxMessageUtil.getAccessToken();
			
			//获取菜单信息
			//String menuStr = wxMenuService.initMenu();
			//菜单初始化
			//wxMessageUtil.initMenu(menuStr);
			
			//获取JSAPI_TICKET
			//wxMessageUtil.getJsapiTicket();
		} catch (Exception e) {
			log.error("获取微信adcessToken出错，信息如下");
		}

	}

}
