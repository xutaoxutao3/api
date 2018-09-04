package com.mtdhb.api.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtdhb.api.dao.UserWXRepository;
import com.mtdhb.api.dto.UserWXDTO;
import com.mtdhb.api.entity.UserWX;
import com.mtdhb.api.service.UserWXService;

/**

 */
@Service
public class UserWXServiceImpl implements UserWXService {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UserWXRepository userRepository;
    
    @Override
    public UserWXDTO getByOpenId(String openId) {
        UserWX userWX = userRepository.findByOpenId(openId);
        UserWXDTO userwxDTO = new UserWXDTO();
        if(userWX==null)
        	return null;
        BeanUtils.copyProperties(userWX, userwxDTO);
        return userwxDTO;
    }


	@Override
	public UserWXDTO registerByPhone(String phone, String openid) {
		UserWX userWX =new UserWX();
		
		userWX.setPhone(phone);
		userWX.setOpenId(openid);
		userWX.setLocked(false);
		userWX.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		userWX.setHp((long)15);
		userRepository.save(userWX);
		
        UserWXDTO userwxDTO = new UserWXDTO();
        BeanUtils.copyProperties(userWX, userwxDTO);
        return userwxDTO;
	}


	@Override
	public UserWXDTO updatePhone(String mobile, UserWXDTO userwxDTO) {
		
		userwxDTO.setPhone(mobile);
		userwxDTO.setGmtModified(new Timestamp(System.currentTimeMillis()));
		UserWX user=new UserWX();
		BeanUtils.copyProperties(userwxDTO, user);
		userRepository.save(user);
		
		return userwxDTO;
	}


	@Override
	public UserWXDTO findByMail(String mail) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UserWXDTO findByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UserWXDTO findByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}
    

}
