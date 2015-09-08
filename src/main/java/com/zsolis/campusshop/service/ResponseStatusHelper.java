package com.zsolis.campusshop.service;

import java.util.*;

public class ResponseStatusHelper {
	public static Map<String, String> getOkResponse() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("status", "ok");
		return result;
	}
	
	public static Map<String, String> getErrorResponse(String reason) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("status", "error");
		result.put("reason", reason);
		return result;
	}
}
