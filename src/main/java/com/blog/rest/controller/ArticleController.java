package com.blog.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.Article;
import com.blog.services.ArticleService;

@RestController
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@GetMapping("/articles")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> articles = service.getAllArticles();
		if (articles.isEmpty()) {
			return new ResponseEntity<List<Article>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
	}

	@GetMapping("/articles/{title}")
	public ResponseEntity<Article> getArticle(@PathVariable("title") String title) {
		Article article = service.getArticleByTitle(title);
		if (article == null) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}

	}

	@GetMapping("/articles/author/{author}")
	public ResponseEntity<List<Article>> getArticleByAuthor(@PathVariable("author") String author) {
		List<Article> articles = service.getArticlesByAuthor(author);
		if (articles.isEmpty()) {
			return new ResponseEntity<List<Article>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
	}

	@PostMapping("/articles")
	public ResponseEntity<Article> addArticle(@RequestBody Article article) {
		try {
			service.createArticle(article);
			return new ResponseEntity<Article>(article, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.CONFLICT);
		}

	}

	@PutMapping("/articles/{title}")
	public ResponseEntity<Article> updateArticle(@PathVariable String title, @RequestBody Article article) {
		try {
			service.updateArticle(title, article);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/articles/{title}")
	public ResponseEntity<String> deleteArticle(@PathVariable String title) {
		try {
			service.deleteArticle(title);
			return new ResponseEntity<String>("Article deleted", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Unable to delete", HttpStatus.NOT_FOUND);
		}
	}
}
