package com.mtdhb.api.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author 
 * @date 
 */
@Data
public class UserWXDTO {

	private static final long serialVersionUID = 2084135877780190423L;
    private Long id=2084135877780190423L;
    private String openId;
    private String name;
    private String salt;
    private String password;//如果openid可能更换就需要
    private String mail;
    private String phone;
    private String token;
    private Long hp;
    private Boolean locked;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
