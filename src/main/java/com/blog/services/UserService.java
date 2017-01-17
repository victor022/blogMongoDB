package com.blog.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.repositories.UserRepository;
import com.google.common.base.Strings;

@Service
public class UserService {

	private final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	public List<User> getAllUser() {
		return repository.findAll();
	}

	public User getUser(String id) {
		return repository.findOne(id);
	}

	public User getUserByDni(String dni) {
		return repository.findByDni(dni);
	}

	public List<User> getUsersByName(String name) {
		return repository.findByName(name);
	}

	public boolean existUser(String id) {
		User user = repository.findOne(id);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean existUser(User user) {
		return existUser(user.getId());
	}

	public boolean existUserWithDni(String dni) {
		User user = getUserByDni(dni);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public User createUser(User user) {
		if (existUserWithDni(user.getDni())) {
			LOG.warn("The user: {} exist in the DB", user);
			throw new IllegalArgumentException();
		} else if (!isUserValid(user)) {
			throw new IllegalArgumentException();
		} else {
			repository.save(user);
			LOG.info("Created user: {}", user);
			return user;
		}
	}

	public User updateUser(String dni, User user) {
		if (!existUser(user.getId())) {
			LOG.warn("User with id {} not found", user.getId());
			throw new IllegalArgumentException();
		} else if (!isUserValid(user)) {
			throw new IllegalArgumentException();
		} else if (!existUserWithDni(dni)) {
			LOG.warn("User with dni {} not found", dni);
			throw new IllegalArgumentException();
		}
		repository.save(user);
		LOG.info("Updated user: {}", user);
		return user;
	}

	public void deleteUser(String dni) {
		User user = repository.findByDni(dni);
		if (user != null) {
			repository.delete(user);
			LOG.info("Deleted user with dni: {}", dni);
		} else {
			LOG.warn("The user with dni {} not exist in the DB", dni);
			throw new IllegalArgumentException();
		}
	}

	public boolean isUserValid(User user) {
		if (Strings.isNullOrEmpty(user.getDni()) || Strings.isNullOrEmpty(user.getName())
				|| Strings.isNullOrEmpty(user.getEmail())) {
			LOG.warn("The user: {} is invalid.", user);
			return false;
		} else {
			return true;
		}
	}

}
