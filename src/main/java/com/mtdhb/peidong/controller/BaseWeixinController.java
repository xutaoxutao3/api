package com.mtdhb.peidong.controller;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 微信验证基类
 * @author zhaopeidong
 *
 */
public class BaseWeixinController {
	
	protected boolean checkSignature(String timestamp, String nonce, String signature){
		ArrayList<String> list=new ArrayList<String>();
		String token = "p0o9i8u7";
		list.add(nonce);
		list.add(timestamp);
		list.add(token);

		Collections.sort(list);
		String sha1Singnature = DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2));

		System.out.println(sha1Singnature);
		if(sha1Singnature.equals(signature)) {
			System.out.println(true);
			return true;
		}
			return false;
	}
	protected boolean checkSignature(HttpServletRequest request){
		String token = request.getParameter("token");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		return checkSignature(timestamp, nonce, signature);
	}
}
