package com.blog.rest.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.entities.Article;
import com.blog.services.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService service;

	@ApiOperation(value = "View a list of articles", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> articles = service.getAllArticles();
		if (articles.isEmpty()) {
			return new ResponseEntity<List<Article>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Search a article by ID", response = Article.class)
	@GetMapping("/show/{id}")
	public ResponseEntity<Article> getArticle(@PathVariable("id") String id) {
		Article article = service.getArticle(id);
		if (article == null) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}

	}

	@PostMapping("/add")
	public ResponseEntity<Article> addArticle(@RequestBody Article article) {
		try {
			service.createArticle(article);
			return new ResponseEntity<Article>(article, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.CONFLICT);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
		try {
			service.updateArticle(article);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteArticle(@PathVariable String id) {
		try {
			service.deleteArticle(id);
			return new ResponseEntity<String>("Article deleted", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Unable to delete", HttpStatus.NOT_FOUND);
		}
	}

	/*
	@GetMapping("show/author/{author}")
	public ResponseEntity<List<Article>> getArticlesByAuthor(@PathVariable("author") String author) {
		List<Article> articles = service.getArticlesByAuthor(author);
		if (articles.isEmpty()) {
			return new ResponseEntity<List<Article>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
	}
*/
}
