package com.zsolis.campusshop.util;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;
import org.springframework.stereotype.Component;

@Component
public class SMSUtil {
	private String userName;
	private String password;
	private CloseableHttpClient httpClient;
	
	public SMSUtil() {
		userName = "";
		password = "";
		httpClient = HttpClients.createDefault();
	}
	
	public int sendSMS(String phoneNumber, String message) {
		int resultCode = -100;
		try {
			String encodedMessage = URLEncoder.encode(message, "GB2312");
			String url = "http://si.800617.com:4400/SendSms.aspx?un=" 
					+ userName + "&pwd=" + password 
					+ "&mobile=" + phoneNumber + "&msg=" + encodedMessage;
			HttpGet request = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				resultCode = getSMSResult(entity);
			}
			response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultCode;
	}
	
	private int getSMSResult(HttpEntity entity) throws ParseException, IOException {
		String entityString = EntityUtils.toString(entity, "GB2312");
		String [] splits = (entityString.substring(0, entityString.length()-1)).split("=");
		return Integer.parseInt(splits[1]);
	}
}
