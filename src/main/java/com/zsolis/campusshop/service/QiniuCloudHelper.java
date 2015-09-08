package com.zsolis.campusshop.service;

import org.springframework.stereotype.Component;

import com.qiniu.util.*;

@Component
public class QiniuCloudHelper {
	private Auth auth;
	
	public QiniuCloudHelper() {
		//填写七牛云的账号密码
		auth = Auth.create("", "");
	}
	
	public String getUploadToken() {
		return auth.uploadToken("campusshop");
	}
}
