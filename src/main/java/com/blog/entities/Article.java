package com.blog.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Log4j
@Document(collection = "articles")
public class Article {

	@Id
	private String id;

	@ApiModelProperty(notes = "The title of the Article", required = true)
	@Indexed(unique = true)
	private String title;

	@ApiModelProperty(notes = "The author of the Article", required = true)
	private String author;

	@ApiModelProperty(notes = "The publication date of the Article")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date publish_date;

	@ApiModelProperty(notes = "The text of the Article", required = true)
	private String text;

	@ApiModelProperty(notes = "The comments of the Article")
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
