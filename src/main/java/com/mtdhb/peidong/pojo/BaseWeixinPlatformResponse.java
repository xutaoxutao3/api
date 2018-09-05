package com.mtdhb.peidong.pojo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@SuppressWarnings("restriction")
@XmlRootElement(name = "xml")
/**
 * 微信后台返回报文
 * @author zhaopeidong
 *
 */
public class BaseWeixinPlatformResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	public BaseWeixinPlatformResponse(){
		this.ToUserName = "";
        this.FromUserName = "";
        this.CreateTime = String.valueOf(System.currentTimeMillis());
	}
	public BaseWeixinPlatformResponse(BaseWeixinPlatformRequest requestPojo) {
		this.ToUserName = requestPojo.getFromUserName();
        this.FromUserName = requestPojo.getToUserName();
        this.CreateTime = String.valueOf(System.currentTimeMillis());
    }  
    public BaseWeixinPlatformResponse(String msgType, BaseWeixinPlatformRequest requestPojo) {  
        this.MsgType = msgType;
        this.ToUserName = requestPojo.getFromUserName();
        this.FromUserName = requestPojo.getToUserName();
        this.CreateTime = String.valueOf(System.currentTimeMillis());
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
		return MsgType;
	}
	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}
}
