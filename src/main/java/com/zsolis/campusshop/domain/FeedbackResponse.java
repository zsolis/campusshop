package com.zsolis.campusshop.domain;

import java.util.*;

import javax.persistence.*;

@Entity
public class FeedbackResponse {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String content;
	private Date date = new Date();
	@ManyToOne
	@JoinColumn(name = "FEEDBACK_ID", nullable = false)
	private Feedback feedback;
	@ManyToOne
	@JoinColumn(name = "ADMINISTRATOR_ID", nullable = false)
	private Administrator administrator;
	
	public FeedbackResponse() {}
	public FeedbackResponse(String content, Feedback feedback, Administrator administrator) {
		super();
		this.content = content;
		this.feedback = feedback;
		this.administrator = administrator;
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
	public Feedback getFeedback() {
		return feedback;
	}
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	public Administrator getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
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
		if (!(obj instanceof FeedbackResponse))
			return false;
		FeedbackResponse other = (FeedbackResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
