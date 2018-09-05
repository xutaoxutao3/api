package com.mtdhb.peidong.action;

import com.mtdhb.peidong.pojo.BaseWeixinPlatformRequest;
import com.mtdhb.peidong.pojo.BaseWeixinPlatformResponse;

public abstract class BaseWeixinMsgAction {
	/**
	 * 业务逻辑
	 * @param requestPojo
	 * @return
	 */
	public abstract BaseWeixinPlatformResponse execute(BaseWeixinPlatformRequest requestPojo);
}
