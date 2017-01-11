package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "articles")
public class Article {

	@Id
	private String id;

	@Indexed(unique = true)
	private String title;

	private String author;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date publish_date;

	private String text;
	
//	@DBRef -> se usa si la entidad de referencia es una colección aparte con id propio
	private List<Comment> comments;

	public Article() {
		
	}
		
	public Article(String title, String author, Date publish_date, String text, ArrayList<Comment> comments) {
		super();
		this.title = title;
		this.author = author;
		this.publish_date = publish_date;
		this.text = text;
		this.comments = comments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return String.format("Article[id=%s, title='%s', author='%s', publish_date='%s', text='%s']", id, title, author, publish_date.toString(), text);
	}

}
