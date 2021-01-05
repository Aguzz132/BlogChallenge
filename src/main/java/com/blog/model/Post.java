package com.blog.model;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "creator")
	private String creator;
	
	@Lob
	@Column(name="content", length=512)
	private String content;
	
	@Column(name = "image", nullable = true)
	private String image;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "date")
	private LocalDate date;
	
	public Post(int id, String title, String creator, String content, String image, String category) {
		super();
		this.id = id;
		this.title = title;
		this.creator = creator;
		this.content = content;
		this.image = image;
		this.category = category;
		this.date = LocalDate.now();
	}

	public Post() {
		super();
		this.date = LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Transient
	public String getPostImagePath() {
		
		if (image == null || id == 0) {
			return null;
		}
		
		return "/post-images/" + id + "/" + image;
	}
	
}
