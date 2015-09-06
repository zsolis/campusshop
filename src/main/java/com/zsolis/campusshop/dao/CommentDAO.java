package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;
import com.zsolis.campusshop.util.ConstantUtil;

@Repository
public class CommentDAO extends DAO{
	public CommentDAO() {}
	
	/**
	 * @return
	 * 获得CommentMap
	 */
	public Map<String, Object> getCommentMapByComment(Comment comment) {
		Map<String, Object> commentMap = new HashMap<String, Object>();
		commentMap.put("id", comment.getId());
		commentMap.put("content", comment.getContent());
		commentMap.put("date", comment.getDate());
		commentMap.put("userId", comment.getUser().getId());
		commentMap.put("userName", comment.getUser().getName());
		commentMap.put("itemId", comment.getItem().getId());
		commentMap.put("orderId", comment.getOrder().getId());
		return commentMap;
	}
	
	/**
	 * @return
	 * 根据ID返回评论
	 */
	public Comment getCommentById(Long commentId) {
		Session session = getSession();
		return (Comment)session.get(Comment.class, commentId);
	}
	
	/**
	 * @return
	 * 根据商品返回评论数量
	 */
	public int getCommentCountByItem(Item item) {
		Session session = getSession();
		Query query = session.createQuery("select c.content from Comment c where c.item = :item and c.status = 0")
				.setEntity("item",item);
		return query.getFetchSize();
	}
	
	/**
	 * @param page
	 * @return
	 * 返回商品的评论，按评论时间排序
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByItemOrderedByDate(Item item, CommentStatus status, int page) {
		Session session = getSession();
		if (status != null) {
			Query query = session.createQuery("from Comment c where c.item = :item and c.status = :status order by c.date desc")
					.setEntity("item", item)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session.createQuery("from Comment c where c.item = :item order by c.date desc")
					.setEntity("item", item)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return
	 * 根据店铺获得评论，按时间降序排列
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByStoreOrderedByDateDesc(Store store, CommentStatus status, int page) {
		Session session = getSession();
		if(status != null) {
			Query query = session .createQuery("from Comment c where c.item.store = :store and c.status = :status order by c.date desc")
					.setEntity("store", store)
					.setInteger("status", status.ordinal())
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		} else {
			Query query = session .createQuery("from Comment c where c.item.store = :store order by c.date desc")
					.setEntity("store", store)
					.setMaxResults(ConstantUtil.MAX_RESAULTS)
					.setFirstResult(page * ConstantUtil.MAX_RESAULTS);
			return query.list();
		}
	}
	
	/**
	 * @return
	 * 添加评论
	 */
	public Long addComment(User user, Item item, Order order, String content) {
		Comment comment = new Comment(user, item, order, content);
		Session session = getSession();
		return (Long)session.save(comment);
	}
}
