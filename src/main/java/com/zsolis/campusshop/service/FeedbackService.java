package com.zsolis.campusshop.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsolis.campusshop.dao.*;
import com.zsolis.campusshop.domain.*;

@Service
public class FeedbackService {
	@Autowired
	private FeedbackDAO feedbackDAO;
	@Autowired
	private FeedbackResponseDAO feedbackResponseDAO;
	
	public FeedbackService() {}
	
	public List<Map<String, Object>> getFeedbacksByUserCampus(Long userId, Long campusId) {
		User user = new TemporaryUser();
		user.setId(userId);
		Campus campus = new Campus();
		campus.setId(campusId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Feedback> feedbacks = feedbackDAO.getFeedbacksByUserCampus(user, campus);
		for(Feedback feedback : feedbacks) {
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("feedback", feedbackDAO.getFeedbackMapByFeedback(feedback));
			group.put("responses", feedbackResponseDAO.getFeedbackResponsesByFeedback(feedback));
			result.add(group);
		}
		return result;
	}
	
	public List<Map<String, Object>> getFeedbacksByCampus(Long campusId, int page) {
		Campus campus = new Campus();
		campus.setId(campusId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Feedback> feedbacks = feedbackDAO.getFeedbacksByCampus(campus, page);
		for(Feedback feedback : feedbacks) {
			Map<String, Object> group = new HashMap<String, Object>();
			group.put("feedback", feedbackDAO.getFeedbackMapByFeedback(feedback));
			group.put("responses", feedbackResponseDAO.getFeedbackResponsesByFeedback(feedback));
			result.add(group);
		}
		return result;
	}
	
	public Long addFeedback(Long userId, Long campusId, String content) {
		User user = new TemporaryUser();
		user.setId(userId);
		Campus campus = new Campus();
		campus.setId(campusId);
		return feedbackDAO.addFeedback(user, campus, content);
	}
	
	public Long addFeedbackResponse(Long feedbackId, Long administratorId, String content) {
		Feedback feedback = new Feedback();
		feedback.setId(feedbackId);
		Administrator administrator = new SuperAdministrator();
		administrator.setId(administratorId);
		return feedbackResponseDAO.addFeedbackResponse(feedback, administrator, content);
	}
}
