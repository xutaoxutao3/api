package com.mtdhb.peidong.action;

import com.mtdhb.peidong.pojo.BaseWeixinPlatformRequest;
import com.mtdhb.peidong.pojo.BaseWeixinPlatformResponse;
import com.mtdhb.peidong.pojo.WeixinTextMsgRequest;
import com.mtdhb.peidong.pojo.WeixinTextMsgResponse;

/**
 * 文本消息处理Action
 * @author zhaopeidong
 *
 */
public class WeixinTextMsgAction extends BaseWeixinMsgAction{

	@Override
	public BaseWeixinPlatformResponse execute(BaseWeixinPlatformRequest requestPojo) {
		String content = ((WeixinTextMsgRequest) requestPojo).getContext();
		return new WeixinTextMsgResponse("I got : " + content, (WeixinTextMsgRequest) requestPojo);
	}
}
