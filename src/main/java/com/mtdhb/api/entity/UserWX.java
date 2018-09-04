package com.mtdhb.api.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author i@huangdenghe.com
 * @date 2017/12/01
 */
@Data
@Entity
public class UserWX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openId;
    private String name;
    private String password;
    private String salt;
    private String mail;
    private String phone;
    private String token;
    @Column(name = "is_locked")
    private Boolean locked;
    private Long hp;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

}
