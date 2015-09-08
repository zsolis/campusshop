package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class CommentResponseDAO extends DAO{
	public CommentResponseDAO() {}
	
	/**
	 * @return List<Map<String, Object>>
	 * new map(c.id as id, c.content as content, c.date as date)
	 * 根据评论返回评论回复
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCommentResponses(Comment comment) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.id as id, c.content as content, c.date as date) from CommentResponse c where c.comment = :comment")
				.setEntity("comment", comment);
		return query.list();
	}
	
	/**
	 * @return
	 * 添加评论反馈
	 */
	public Long addCommentResponse(Comment comment, String content) {
		CommentResponse commentResponse = new CommentResponse(content, comment);
		Session session = getSession();
		return (Long)session.save(commentResponse);
	}
}
