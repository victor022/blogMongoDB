package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
@Log4j
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

		
	public Article(String title, String author, Date publish_date, String text, ArrayList<Comment> comments) {
		super();
		this.title = title;
		this.author = author;
		this.publish_date = publish_date;
		this.text = text;
		this.comments = comments;
	}

}
