package com.blog.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Article;
import com.blog.repositories.ArticleRepository;

@Service
public class ArticleService {
	
	private final Logger LOG = LoggerFactory.getLogger(ArticleService.class);
	
	@Autowired
	private ArticleRepository repository;
	
	public List<Article> getAllArticles(){
		return repository.findAll();
	}
	
	public Article getArticle(String id){
		return repository.findOne(id);
	}
	
	public Article getArticleByTitle(String title){
		return repository.findByTitle(title);
	}
	
	public List<Article> getArticlesByAuthor(String author){
		return repository.findByAuthor(author);
	}
	
	public boolean existArticle(String id) {
		Article article = repository.findOne(id);
		if (article != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean existArticle(Article article) {
		return existArticle(article.getId());
	}
	
	public boolean existArticleWithTitle(String title) {
		Article article = getArticleByTitle(title);
		if (article != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Article createArticle(Article article) {
		if (existArticleWithTitle(article.getTitle())) {
			LOG.warn("The article: {} exist in the DB", article);
			throw new IllegalArgumentException();
		} else {
			repository.save(article);
			LOG.info("Created article: {}", article);
			return article;
		}
	}
	
	public Article updateArticle(String title, Article article) {
		if (!existArticle(article.getId())) {
			LOG.warn("Article with id {} not found", article.getId());
			throw new IllegalArgumentException();
		} else if (!existArticleWithTitle(title)) {
			LOG.warn("Article with title {} not found", title);
			throw new IllegalArgumentException();
		}
		repository.save(article);
		LOG.info("Updated article: {}", article);
		return article;
	}

	public void deleteArticle(String title) {
		Article article = repository.findByTitle(title);
		if (article != null) {
			repository.delete(article);
			LOG.info("Deleted article with title: {}", title);
		} else {
			LOG.warn("The article with title {} not exist in the DB", title);
			throw new IllegalArgumentException();
		}
	}

}
