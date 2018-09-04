/**
 * 
 */
package com.mtdhb.api.service;

import com.mtdhb.api.dto.UserWXDTO;
import com.mtdhb.api.entity.UserWX;

/**
 * @author i@huangdenghe.com
 * @date 2017/12/02
 */
public interface UserWXService {
	
	UserWXDTO getByOpenId(String openId);
	
	UserWXDTO registerByPhone(String Phone,String openid);

	UserWXDTO updatePhone(String mobile, UserWXDTO userwxDTO);
	

	UserWXDTO findByMail(String mail);

	UserWXDTO findByToken(String token);
	
	UserWXDTO findByPhone(String phone);

	
//    UserDTO loginByMail(String account, String password);
//
//    UserDTO registerByMail(AccountDTO accountDTO);
//
//    /**
//     * 发送注册邮件
//     * 
//     * @param mail
//     */
//    void sendRegisterMail(String mail);
//
//    UserDTO resetPassword(AccountDTO accountDTO);
//
//    /**
//     * 发送重置密码邮件
//     * 
//     * @param mail
//     */
//    void sendResetPasswordMail(String mail);
//
//    UserDTO getByToken(String token);
//
//    long getAvailable(ThirdPartyApplication application, long userId);
//
//    NumberDTO getNumber(ThirdPartyApplication application, long userId);

}
