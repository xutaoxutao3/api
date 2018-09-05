package com.mtdhb.peidong.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mtdhb.peidong.constant.WeixinMsgType;


/**
 * 微信后台文本消息请求
 * @author zhaopeidong
 *
 */
@XmlRootElement(name = "xml")
public class WeixinTextMsgRequest extends BaseWeixinPlatformRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5313125628058534493L;
	private String Content;

	public String getContext() {
		return Content;
	}
	@XmlElement(name = "Content")
	public void setContext(String context) {
		this.Content = WeixinMsgType.text;
		this.Content = context;
	}
}
