package com.blog.entities;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(notes = "The author of the comment", required = true)
	private String author;

	@ApiModelProperty(notes = "The date of the comment")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;

	@ApiModelProperty(notes = "The text of the comment", required = true)
	private String text;

}
