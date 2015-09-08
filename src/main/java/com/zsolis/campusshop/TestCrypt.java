package com.zsolis.campusshop;

import com.zsolis.campusshop.util.CryptUtil;

public class TestCrypt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CryptUtil cryptUtil = new CryptUtil();
		System.out.println(cryptUtil.getSalt());
		String encrypt = cryptUtil.encryptSHA1("password123TTAA");
		System.out.println(encrypt);
		String afterSalt = cryptUtil.encryptSHA1(encrypt + cryptUtil.getSalt());
		System.out.println(afterSalt);
		boolean result = cryptUtil.comparePassword(afterSalt, encrypt);
		System.out.println(result);
	}

}
