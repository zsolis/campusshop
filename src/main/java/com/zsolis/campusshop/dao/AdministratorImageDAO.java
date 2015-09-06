package com.zsolis.campusshop.dao;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.zsolis.campusshop.domain.*;

@Repository
public class AdministratorImageDAO extends DAO{
	public AdministratorImageDAO() {}
	
	/**
	 * @return
	 * ����ID���ع���ԱͼƬ
	 */
	public AdministratorImage getAdministratorImageById(Long administratorImageId) {
		Session session = getSession();
		return (AdministratorImage)session.get(AdministratorImage.class, administratorImageId);
	}
	
	/**
	 * @return
	 * ���ع���Ա���ϴ���ͼƬ
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAdministratorImages(Administrator administrator) {
		Session session = getSession();
		Query query = session.createQuery("select new map(a.id as id, a.path as path) from AdministratorImage a where a.administrator = :administrator")
				.setEntity("administrator",administrator);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>
	 * new map(c.location as location, c.url as url, c.image.path as imagePath)
	 * ����У԰��ҳ���ֲ�ͼƬ����λ������
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusCarouselImagesOrderedByLocation(Campus campus) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.location as location, c.url as url, c.image.path as imagePath) from CampusRecommendImage c where c.campus = :campus and c.location < 10 order by c.location asc")
				.setEntity("campus", campus);
		return query.list();
	}
	
	/**
	 * @return List<Map<String, Object>
	 * new map(c.location as location, c.url as url, c.image.path as imagePath)
	 * ����У԰��ҳ�Ĺ��ͼƬ����λ������
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCampusAdImagesOrderedByLocation(Campus campus) {
		Session session = getSession();
		Query query = session.createQuery("select new map(c.location as location, c.url as url, c.image.path as imagePath) from CampusRecommendImage c where c.campus = :campus and c.location > 10 order by c.location asc")
				.setEntity("campus", campus);
		return query.list();
	}
	
	/**
	 * @return
	 * �����ƷͼƬ
	 */
	public Long addAdministratorImage(Administrator administrator, String path) {
		AdministratorImage image = new AdministratorImage(path, administrator);
		Session session = getSession();
		return (Long)session.save(image);
	}
	
	/**
	 * ���У԰�Ƽ�ͼƬ
	 */
	public void addCampusRecommendImage(Campus campus, AdministratorImage image, Long location, String url) {
		CampusRecommendImage campusRecommendImage = new CampusRecommendImage(campus, image, location, url);
		Session session = getSession();
		session.save(campusRecommendImage);
	}
	
	/**
	 * ɾ��У԰�Ƽ�ͼƬ
	 */
	public void removeCampusRecommendImage(Campus campus, AdministratorImage image) {
		CampusRecommendImage campusRecommendImage = new CampusRecommendImage(campus, image, 0L, "");
		Session session = getSession();
		session.delete(campusRecommendImage);
	}
	
	/**
	 * ����У԰�Ƽ�ͼƬ
	 */
	public CampusRecommendImage getCampusRecommendImage(Campus campus, AdministratorImage image) {
		Session session = getSession();
		Query query = session.createQuery("from CampusRecommendImage c where c.campus = :campus and c.image = :image")
				.setEntity("campus", campus)
				.setEntity("image", image);
		return (CampusRecommendImage)query.uniqueResult();
	}
}
