package com.zsolis.campusshop;

import com.zsolis.campusshop.util.SMSUtil;

public class TestSMS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SMSUtil smsUtil = new SMSUtil();
		smsUtil.sendSMS("15100200868", "这是一个测试哦551022，from 章阳【购了么】");
	}

}
