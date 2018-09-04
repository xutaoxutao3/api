package com.mtdhb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtdhb.api.entity.UserWX;

/**
 */
public interface UserWXRepository extends JpaRepository<UserWX, Long> {

	UserWX findByMail(String mail);

	UserWX findByToken(String token);
	
	UserWX findByOpenId(String openId);
	
	UserWX findByPhone(String phone);

}
