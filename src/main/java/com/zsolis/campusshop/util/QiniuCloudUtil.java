package com.zsolis.campusshop.util;

import org.springframework.stereotype.Component;

import com.qiniu.util.*;

@Component
public class QiniuCloudUtil {
	Auth auth;
	
	public QiniuCloudUtil() {
		//��д��ţ�Ƶ��˺�����
		auth = Auth.create("", "");
	}
	
	public String getUploadToken() {
		return auth.uploadToken("campusshop");
	}
}
