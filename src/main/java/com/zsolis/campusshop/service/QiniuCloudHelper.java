package com.zsolis.campusshop.service;

import org.springframework.stereotype.Component;

import com.qiniu.util.*;

@Component
public class QiniuCloudHelper {
	private Auth auth;
	
	public QiniuCloudHelper() {
		//ÌîĞ´ÆßÅ£ÔÆµÄÕËºÅÃÜÂë
		auth = Auth.create("", "");
	}
	
	public String getUploadToken() {
		return auth.uploadToken("campusshop");
	}
}
