package com.zsolis.campusshop.util;

import org.springframework.stereotype.Component;

import com.qiniu.util.*;

@Component
public class QiniuCloudUtil {
	Auth auth;
	
	public QiniuCloudUtil() {
		auth = Auth.create("jVgnpro4xBAoZ6co2CVsTrKwpzsEpT5pfaIokK8G", "e2Rc6I3ov2AObbMnHTZVxV_8asDOiwDabZs74_5_");
	}
	
	public String getUploadToken() {
		return auth.uploadToken("campusshop");
	}
}
