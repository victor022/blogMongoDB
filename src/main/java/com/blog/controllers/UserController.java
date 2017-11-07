package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.User;
import com.blog.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = service.getAllUser();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		User user = service.getUser(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/users/dni/{dni}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUserByDni(@PathVariable("dni") String dni) {
		User user = service.getUserByDni(dni);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/users/name/{userName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<User>> getUserByName(@PathVariable("userName") String userName) {
		List<User> users = service.getUsersByName(userName);
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		try {
			service.createUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping(value = "/users/dni/{dni}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable String dni, @RequestBody User user) {
		try {
			service.updateUser(dni, user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/dni/{dni}")
	public ResponseEntity<String> deleteUser(@PathVariable String dni) {
		try {
			service.deleteUser(dni);
			return new ResponseEntity<String>("User deleted", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("Unable to delete", HttpStatus.NOT_FOUND);
		}
	}
}
