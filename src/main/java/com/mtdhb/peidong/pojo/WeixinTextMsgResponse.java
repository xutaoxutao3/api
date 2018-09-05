package com.mtdhb.peidong.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mtdhb.peidong.constant.WeixinMsgType;


/**
 * 文本消息回复返回报文
 * @author zhaopeidong
 *
 */
@XmlRootElement(name = "xml")
public class WeixinTextMsgResponse extends BaseWeixinPlatformResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3453774645696571988L;
	private String Content;
	
	public WeixinTextMsgResponse(){
		super();
	}
	public WeixinTextMsgResponse(String content, WeixinTextMsgRequest requestPojo){
		super(WeixinMsgType.text, requestPojo);
		this.setContent(content);
	}

	public String getContent() {
		return Content;
	}
	@XmlElement(name = "Content")
	public void setContent(String content) {
		this.Content = content;
	}
}
