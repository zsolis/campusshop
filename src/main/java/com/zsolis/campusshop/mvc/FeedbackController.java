package com.zsolis.campusshop.mvc;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.service.*;

@Controller
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/feedbacksbyuser/{userId}/{campusId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getFeedbacksByUserCampus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("campusId") Long campusId) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return null;
		}
		return feedbackService.getFeedbacksByUserCampus(userId, campusId);
	}
	
	@RequestMapping(value = "/feedbacksbycampus/{campusId}/{page}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getFeedbacksByCampus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("campusId") Long campusId,
			@PathVariable("page") int page) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return null;
		}
		return feedbackService.getFeedbacksByCampus(campusId, page);
	}
	
	@RequestMapping(value = "/addfeedback/{userId}/{campusId}/{content}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addFeedback(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("campusId") Long campusId, 
			@PathVariable("content") String content) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sesstionToken");
		}
		if (feedbackService.addFeedback(userId, campusId, content) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/addfeedbackresponse/{administratorId}/{feedbackId}/{content}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addFeedbackResponse(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("administratorId") Long administratorId, 
			@PathVariable("feedbackId") Long feedbackId, 
			@PathVariable("content") String content) {
		if (!sessionTokenHelper.checkSessionTokenId("Admin", administratorId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (feedbackService.addFeedbackResponse(feedbackId, administratorId, content) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
}
