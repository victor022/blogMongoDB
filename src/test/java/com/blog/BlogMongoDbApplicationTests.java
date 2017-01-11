package com.blog;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogMongoDbApplicationTests {

//	@Autowired
//	private UserController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void checkAllUsers() throws Exception {
		this.mockMvc.perform(get("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
