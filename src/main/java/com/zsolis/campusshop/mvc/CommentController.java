package com.zsolis.campusshop.mvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zsolis.campusshop.domain.CommentStatus;
import com.zsolis.campusshop.service.*;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private SessionTokenHelper sessionTokenHelper;
	
	@RequestMapping(value = "/commentsbyitem/{itemId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCommentsByItem(@PathVariable("itemId") Long itemId, 
			@PathVariable("page") int page) {
		return commentService.getCommentsByItem(itemId, page);
	}
	
	@RequestMapping(value = "/commentsbystore/{storeId}/{page}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> getCommentsByStore(@PathVariable("storeId") Long storeId, 
			@PathVariable("page") int page) {
		return commentService.getCommentsByStore(storeId, page);
	}
	
	@RequestMapping(value = "/addcomment/{userId}/{itemId}/{orderId}/{content}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addComment(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("userId") Long userId, 
			@PathVariable("itemId") Long itemId, 
			@PathVariable("orderId") Long orderId, 
			@PathVariable("content") String content) {
		if (!sessionTokenHelper.checkSessionTokenId("User", userId, sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (commentService.addComment(userId, itemId, orderId, content) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/addcommentresponse/{commentId}/{content}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> addCommentResponse(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("commentId") Long commentId, 
			@PathVariable("content") String content) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		if (commentService.addCommentResponse(commentId, content) == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return ResponseStatusHelper.getOkResponse();
	}
	
	@RequestMapping(value = "/reportcomment/{commentId}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> reportComment(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("commentId") Long commentId) {
		if (!sessionTokenHelper.checkSessionTokenRole("Store", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		Map<String, String> result = commentService.changeCommentStatus(commentId, CommentStatus.reported);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
	
	@RequestMapping(value = "/changecommentstatus/{commentId}/{status}/{sessionToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, String> changeCommentStatus(@PathVariable("sessionToken") String sessionToken,
			@PathVariable("commentId") Long commentId,
			@PathVariable("status") String statusString) {
		if (!sessionTokenHelper.checkSessionTokenRole("Admin", sessionToken)) {
			return ResponseStatusHelper.getErrorResponse("sessionToken");
		}
		CommentStatus status = CommentStatus.valueOf(statusString);
		if (status == null) {
			return ResponseStatusHelper.getErrorResponse("status错误");
		}
		Map<String, String> result = commentService.changeCommentStatus(commentId, status);
		if (result == null) {
			return ResponseStatusHelper.getErrorResponse("写入错误");
		}
		return result;
	}
}
