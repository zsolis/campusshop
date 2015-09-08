package com.zsolis.campusshop;

import com.zsolis.campusshop.service.QiniuCloudHelper;

public class TestQiniu {

	public static void main(String[] args) {
		QiniuCloudHelper qiniuUtil = new QiniuCloudHelper();
		String uploadToken = qiniuUtil.getUploadToken();
		System.out.println(uploadToken);
	}
}
