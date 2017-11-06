package com.blog.entities;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Log4j
@Document(collection = "users")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	@Indexed(unique = true)
	private String dni;

	private String name;

	private String email;

	public User(String dni, String name, String email) {
		super();
		this.dni = dni;
		this.name = name;
		this.email = email;
	}

}
