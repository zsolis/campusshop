package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class FeedbackResponseDAO extends DAO{
	public FeedbackResponseDAO() {}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(f.id as responseId, f.content as content, f.date as date, f.administrator.id as administratorId, f.adminstrator.name as administratorName)
	 * 根据反馈返回反馈回复
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFeedbackResponsesByFeedback(Feedback feedback) {
		Session session = getSession();
		Query query = session.createQuery("select new map(f.id as responseId, f.content as content, f.date as date, f.administrator.id as administratorId, f.adminstrator.name as administratorName) from FeedbackResponse f where f.feedback = :feedback")
				.setEntity("feedback", feedback);
		return query.list();
	}
	
	/**
	 * @return
	 * 添加反馈回复
	 */
	public Long addFeedbackResponse(Feedback feedback, Administrator administrator, String content) {
		FeedbackResponse feedbackResponse = new FeedbackResponse(content, feedback, administrator);
		Session session = getSession();
		return (Long)session.save(feedbackResponse);
	}
}
