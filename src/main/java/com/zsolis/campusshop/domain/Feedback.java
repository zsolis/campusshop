package com.zsolis.campusshop.domain;

import java.util.*;

import javax.persistence.*;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String content;
	private Date date = new Date();
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "CAMPUS_ID", nullable = false)
	private Campus campus;
	
	public Feedback() {}
	public Feedback(String content, User user, Campus campus) {
		this.content = content;
		this.user = user;
		this.campus = campus;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Feedback))
			return false;
		Feedback other = (Feedback) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
