package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class QiniuCloudController {
	@Autowired
	private QiniuCloudHelper qiniuCloudHelper;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/uploadtoken/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> getUploadToken(@PathVariable("sessionToken") String sessionToken) {
		if (!sessionTokenHelper.checkSessionTokenExist(sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> uploadToken = new HashMap<String, String>();
		uploadToken.put("uploadToken", qiniuCloudHelper.getUploadToken());
		return uploadToken;
	}
}
