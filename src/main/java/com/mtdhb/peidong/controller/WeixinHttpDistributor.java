package com.mtdhb.peidong.controller;

import java.util.Map;

/**
 * 微信请求分流器
 * @author zhaopeidong
 *
 */
public class WeixinHttpDistributor {
	private Map distributeBean;

	public Map getDistributeBean() {
		return distributeBean;
	}

	public void setDistributeBean(Map distributeBean) {
		this.distributeBean = distributeBean;
	}
//	public String getDistributedViewPath(){
//		
//	}
}
