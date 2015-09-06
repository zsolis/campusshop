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
	 * ���CommentMap
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
	 * ����ID��������
	 */
	public Comment getCommentById(Long commentId) {
		Session session = getSession();
		return (Comment)session.get(Comment.class, commentId);
	}
	
	/**
	 * @return
	 * ������Ʒ������������
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
	 * ������Ʒ�����ۣ�������ʱ������
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
	 * ���ݵ��̻�����ۣ���ʱ�併������
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
	 * �������
	 */
	public Long addComment(User user, Item item, Order order, String content) {
		Comment comment = new Comment(user, item, order, content);
		Session session = getSession();
		return (Long)session.save(comment);
	}
}
