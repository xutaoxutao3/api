package com.mtdhb.api.web;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mtdhb.api.constant.CustomHttpHeaders;
import com.mtdhb.api.constant.e.ErrorCode;
import com.mtdhb.api.dao.GirlRepository;
import com.mtdhb.api.dto.UserDTO;
import com.mtdhb.api.dto.UserWXDTO;
import com.mtdhb.api.entity.Girl;
import com.mtdhb.api.exception.BusinessException;
import com.mtdhb.api.service.UserService;
import com.mtdhb.api.service.UserWXService;

/**
 * @author i@huangdenghe.com
 * @date 2018/03/03
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private UserService userService;
    @Autowired
    private GirlRepository girlR;
    @Autowired
    private UserWXService userwxService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String userToken = request.getHeader(CustomHttpHeaders.X_USER_TOKEN);
        logger.info("method={}, uri={}, userToken={}", method, uri, userToken);
//        if (userToken == null) {
//            throw new BusinessException(ErrorCode.AUTHENTICATION_EXCEPTION, "method={}, uri={}, userToken={}", method,
//                    uri, userToken);
//        }
        

/*        Girl g=new Girl();
        g.setAge(userToken);
        Girl g1=new Girl();
        g1.setAge(userToken);
        g1.setCupSize("f");
        girlR.save(g);
        girlR.save(g1);
        
        List<Girl> list=girlR.findAll();*/
//        
        UserDTO userDTO = userService.getByToken(userToken);
        UserWXDTO userwxDTO=userwxService.getByOpenId(userToken);
//        
        if (userDTO != null) {
        	RequestContextHolder.set(userDTO);
        }
        if (userwxDTO != null){
           RequestWXContextHolder.set(userwxDTO);
        }
       
        if(userDTO==null && userwxDTO == null){
        	throw new BusinessException(ErrorCode.AUTHENTICATION_EXCEPTION,
                    "method={}, uri={}, userToken={}, userDTO={},userwxDTO={}", method, uri, userToken, userDTO,userwxDTO);
        }
        
        if (userDTO!=null && userDTO.getLocked()) {
            throw new BusinessException(ErrorCode.USER_LOCKED, "method={}, uri={}, userToken={}, userDTO={}", method,
                    uri, userToken, userDTO);
        } 
        if (userwxDTO !=null && userwxDTO.getLocked()) {
            throw new BusinessException(ErrorCode.USER_LOCKED, "method={}, uri={}, userToken={}, userDTO={}", method,
                    uri, userToken, userDTO);
        }
        /**/
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        RequestContextHolder.reset();
    }

}
