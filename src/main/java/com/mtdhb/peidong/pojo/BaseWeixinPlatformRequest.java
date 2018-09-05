package com.mtdhb.peidong.pojo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "xml")
/**
 * 微信后台请求
 * @author zhaopeidong
 *
 */
public class BaseWeixinPlatformRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600998566792430171L;
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String MsgId;
	public BaseWeixinPlatformRequest() {  
        super();  
    }
	public String getToUserName() {
		return ToUserName;
	}
	@XmlElement(name = "ToUserName")
	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	@XmlElement(name = "FromUserName")
	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	@XmlElement(name = "CreateTime")
	public void setCreateTime(String createTime) {
		this.CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType.trim();
	}
	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	@XmlElement(name = "MsgId")
	public void setMsgId(String msgId) {
		this.MsgId = msgId;
	}
}
