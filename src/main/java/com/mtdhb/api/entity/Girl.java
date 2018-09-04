package com.mtdhb.api.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mtdhb.api.constant.e.HttpService;
import com.mtdhb.api.constant.e.ThirdPartyApplication;

import lombok.Data;

/**
 * @author i@huangdenghe.com
 * @date 2018/03/03
 */
@Data
@Entity
public class Girl {

    @Id
    @GeneratedValue()
    private Long id;
    private String age;
    private String cupSize;
    
    public Girl(){
    	
    }
 

}
