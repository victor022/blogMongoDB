package com.blog.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j
public class Comment {
	
	private String author;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;

	private String text;

}
