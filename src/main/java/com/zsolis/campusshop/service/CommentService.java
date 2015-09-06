package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class CommentService {
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private CommentResponseDAO commentResponseDAO;
	
	public CommentService() {}
	
	public List<Map<String, Object>> getCommentsByItem(Long itemId, int page) {
		Item item = new CommonItem();
		item.setId(itemId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Comment> comments = commentDAO.getCommentsByItemOrderedByDate(item, CommentStatus.normal, page);
		for(Comment comment : comments) {
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("comment", commentDAO.getCommentMapByComment(comment));
			group.put("commentResponses", commentResponseDAO.getCommentResponses(comment));
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getCommentsByStore(Long storeId, int page) {
		Store store = new Store();
		store.setId(storeId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Comment> comments = commentDAO.getCommentsByStoreOrderedByDateDesc(store, null, page);
		for(Comment comment : comments) {
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("comment", commentDAO.getCommentMapByComment(comment));
			group.put("commentResponses", commentResponseDAO.getCommentResponses(comment));
			result.add(group);
		}
		return result;
	}
	
	public Long addComment(Long userId, Long itemId, Long orderId, String content) {
		User user = new TemporaryUser();
		user.setId(userId);
		Item item = new CommonItem();
		item.setId(itemId);
		Order order = new CashOrder();
		order.setId(orderId);
		return commentDAO.addComment(user, item, order, content);
	}
	
	public Long addCommentResponse(Long commentId, String content) {
		Comment comment = new Comment();
		comment.setId(commentId);
		return commentResponseDAO.addCommentResponse(comment, content);
	}
	
	public void changeCommentStatus(Long commentId, CommentStatus status) {
		Comment comment = commentDAO.getCommentById(commentId);
		if(comment.getStatus() == status) {
			return;
		}
		comment.setStatus(status);
	}
}
