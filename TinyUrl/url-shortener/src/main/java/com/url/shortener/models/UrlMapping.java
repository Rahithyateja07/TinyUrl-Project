package com.url.shortener.models;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class UrlMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String originalUrl;
	private String shortUrl;
	private int clickCount =0;
	private LocalDateTime createdTime;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy = "urlMapping")
	private List<ClickEvent> clickEvents;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOriginalUrl() {
		return originalUrl;
	}


	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}


	public String getShortUrl() {
		return shortUrl;
	}


	public void setShortlUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}


	public int getClickCount() {
		return clickCount;
	}


	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}


	public LocalDateTime getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<ClickEvent> getClickEvents() {
		return clickEvents;
	}


	public void setClickEvents(List<ClickEvent> clickEvents) {
		this.clickEvents = clickEvents;
	}
	
	
	

}
