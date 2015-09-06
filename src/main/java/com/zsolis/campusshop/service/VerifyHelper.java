package com.zsolis.campusshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.util.*;

@Component
public class VerifyHelper {
	@Autowired
	private SMSUtil smsUtil;
	@Autowired
	private JedisVerifyDAO jedisVerifyDAO;
	
	public VerifyHelper() {}
	
	public boolean verifyInput(String phoneNumber, String input) {
		String verifyCode = jedisVerifyDAO.getVerifyCode(phoneNumber);
		if(verifyCode == null) {
			return false;
		}
		return verifyCode.equals(input);
	}
	
	public int sendVerifyCode(String phoneNumber) {
		String verifyCode =  ((Double)(Math.random() * 9000 + 1000)).toString().substring(0, 4);
		String message = "您的验证码为" + verifyCode + "，感谢您的使用【购了么】";
		int smsResult = smsUtil.sendSMS(phoneNumber, message);
		if(smsResult == 1) {
			jedisVerifyDAO.setVerifyCode(phoneNumber, verifyCode);
		}
		return smsResult;
	}
}
