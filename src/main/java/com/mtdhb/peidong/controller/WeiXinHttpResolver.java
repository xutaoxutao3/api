package com.mtdhb.peidong.controller;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class WeiXinHttpResolver {
	private Map requestPoJoMap;//请求报文
	private Map responsePoJoMap;//返回报文
	private Map executeMap;//业务逻辑Action
	
	public Map getRequestPoJoMap() {
		return requestPoJoMap;
	}
	public void setRequestPoJoMap(Map requestPoJoMap) {
		this.requestPoJoMap = requestPoJoMap;
	}
	public Map getResponsePoJoMap() {
		return responsePoJoMap;
	}
	public void setResponsePoJoMap(Map responsePoJoMap) {
		this.responsePoJoMap = responsePoJoMap;
	}
	public Map getExecuteMap() {
		return executeMap;
	}
	public void setExecuteMap(Map executeMap) {
		this.executeMap = executeMap;
	}
}
