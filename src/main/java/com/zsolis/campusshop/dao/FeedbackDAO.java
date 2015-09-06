package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.ConstantUtil;

@Repository
public class FeedbackDAO extends DAO{
	public FeedbackDAO() {}
	
	/**
	 * @return
	 * 根据Feedback返回feedbackMap,不访问数据库
	 */
	public Map<String, Object> getFeedbackMapByFeedback(Feedback feedback) {
		Map<String, Object> feedbackMap = new HashMap<String, Object>();
		feedbackMap.put("feedbackId", feedback.getId());
		feedbackMap.put("date", feedback.getDate());
		feedbackMap.put("content", feedback.getContent());
		feedbackMap.put("userId", feedback.getUser().getId());
		feedbackMap.put("userName", feedback.getUser().getName());
		return feedbackMap;
	}
	
	/**
	 * @return
	 * 根据用户返回反馈列表
	 */
	@SuppressWarnings("unchecked")
	public List<Feedback> getFeedbacksByUserCampus(User user, Campus campus) {
		Session session = getSession();
		Query query = session.createQuery("from Feedback f where f.user = :user and f.campus = :campus")
				.setEntity("user", user)
				.setEntity("campus", campus);
		return query.list();
	}
	
	/**
	 * @return
	 * 根据校园返回反馈列表
	 */
	@SuppressWarnings("unchecked")
	public List<Feedback> getFeedbacksByCampus(Campus campus, int page) {
		Session session = getSession();
		Query query = session.createQuery("from Feedback f where f.campus = :campus")
				.setEntity("campus", campus)
				.setMaxResults(ConstantUtil.MAX_RESAULTS)
				.setFirstResult(ConstantUtil.MAX_RESAULTS * page);
		return query.list();
	}
	
	/**
	 * @return
	 * 添加反馈，返回ID
	 */
	public Long addFeedback(User user, Campus campus, String content) {
		Feedback feedback = new Feedback();
		feedback.setCampus(campus);
		feedback.setUser(user);
		feedback.setContent(content);
		Session session = getSession();
		return (Long)session.save(feedback);
	}
}
