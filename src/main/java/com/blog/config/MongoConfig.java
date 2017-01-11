package com.blog.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages="com.blog.repositories")
public class MongoConfig {

}
