package com.blog.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByDni(String dni);
	
	List<User> findByName(String name);
	
}
