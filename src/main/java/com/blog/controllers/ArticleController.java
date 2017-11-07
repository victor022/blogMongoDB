package com.blog.controllers;

import java.util.List;

import com.blog.entities.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.entities.Article;
import com.blog.services.ArticleService;

@Api(value="articlesBlog", description="Operations pertaining to Articles in Blog")
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

	@ApiOperation(value = "Search an article by ID", response = Article.class)
	@GetMapping("/show/{id}")
	public ResponseEntity<Article> getArticle(@PathVariable("id") String id) {
		Article article = service.getArticle(id);
		if (article == null) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Add an article", response = Article.class)
	@PostMapping("/add")
	public ResponseEntity<Article> addArticle(@RequestBody Article article) {
		// TODO: falta añadir la comprobación del JSON para confirmar los campos obligatorios
		try {
			Article newArticle = service.createArticle(article);
			return new ResponseEntity<Article>(newArticle, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.CONFLICT);
		}
	}

	@ApiOperation(value = "Update an article", response = Article.class)
	@PutMapping("/update/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
		try {
			Article newArticle = service.updateArticle(article);
			return new ResponseEntity<Article>(newArticle, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Delete an article")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteArticle(@PathVariable String id) {
		try {
			service.deleteArticle(id);
			return new ResponseEntity<String>("Article deleted", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Unable to delete", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "List comments of article", response = List.class)
	@GetMapping("/{idArticle}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsOfArticle(@PathVariable("idArticle") String idArticle) {
		List<Comment> comments = service.getArticle(idArticle).getComments();
		if (comments == null) {
			return new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Add a comment to the article", response = Comment.class)
	@PostMapping("/{idArticle}/comments/add")
	public ResponseEntity<Article> addComment(@RequestBody Comment comment, @PathVariable("idArticle") String idArticle) {
		// TODO: falta añadir la comprobación del JSON para confirmar los campos obligatorios
		try {
			Article article = service.addComment(idArticle, comment);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
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
