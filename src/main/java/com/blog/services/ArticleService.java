package com.blog.services;

import com.blog.entities.Article;
import com.blog.entities.Comment;
import com.blog.repositories.ArticleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
	
	private final Logger LOG = Logger.getLogger(ArticleService.class);
	
	@Autowired
	private ArticleRepository repository;
	
	public List<Article> getAllArticles(){
		return repository.findAll();
	}
	
	public Article getArticle(String id){
		return repository.findOne(id);
	}

	public Article createArticle(Article article) {
		if (existArticleWithTitle(article.getTitle())) {
			LOG.warn("The article with title: " + article.getTitle() + " exist in the DB");
			throw new IllegalArgumentException();
		} else {
			// Añadimos la fecha actual
			article.setPublishDate(new Date());
			repository.save(article);
			LOG.info("Created article: " + article.toString());
			return article;
		}
	}
	
	public Article updateArticle(Article article) {
		if (!existArticle(article.getId())) {
			LOG.warn("Article with id: " + article.getId() + " not found");
			throw new IllegalArgumentException();
		}
		repository.save(article);
		LOG.info("Updated article: " + article.toString());
		return article;
	}

	public void deleteArticle(String id) {
		Article article = repository.findOne(id);
		if (article != null) {
			repository.delete(article);
			LOG.info("Deleted article with id: " + id);
		} else {
			LOG.warn("The article with id:" + id + " not exist in the DB");
			throw new IllegalArgumentException();
		}
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

	public Article addComment(String idArticle, Comment comment) {
		Article article = getArticle(idArticle);
		if (article == null) {
			LOG.warn("The article with id:" + idArticle + " not exist in the DB");
			throw new IllegalArgumentException();
		} else {
			// Añadimos la fecha actual
			comment.setDate(new Date());
			List<Comment> comments = article.getComments();
			comments.add(comment);
			article.setComments(comments);
			repository.save(article);
			LOG.info("Add comment: " + comment.toString() + ", to article with id:" + idArticle);
			return article;
		}
	}
}
