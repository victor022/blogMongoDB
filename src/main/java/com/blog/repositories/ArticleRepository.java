package com.blog.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blog.entities.Article;

public interface ArticleRepository  extends MongoRepository<Article, String> {
	
	Article findByTitle(String title);
	
	List<Article> findByAuthor(String author);

}
