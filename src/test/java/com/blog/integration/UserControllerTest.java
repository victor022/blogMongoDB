package com.blog.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.entities.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * La persistencia se mantiene en BD una vez finalizados los Tests. No olvide
 * borrar los documentos creados.
 * 
 * @author vmvalle
 *
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	private static User userTest;

	private static String idUserTest;
	private static String dniUserTest;
	private static String nameUserTest;
	private static String emailUserTest;

	@BeforeClass
	public static void setup() {
		dniUserTest = "0000000400";
		nameUserTest = "NuevoUsuario";
		emailUserTest = "soynuevo@gmail.com";
		userTest = new User(dniUserTest, nameUserTest, emailUserTest);
	}

	@AfterClass
	public static void cleanUp() {
		// Clean DB here
	}

	@Test
	public void test001InsertNewUser() {
		ResponseEntity<User> response = this.restTemplate.postForEntity("/users", userTest, User.class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		User user = response.getBody();
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest));
		Assert.assertTrue("User name is not equals.", user.getName().equals(nameUserTest));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
		idUserTest = user.getId();
	}

	@Test
	public void test002GetAllUsersAsArray() {
		ResponseEntity<User[]> response = this.restTemplate.getForEntity("/users", User[].class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		User[] users = response.getBody();
		Assert.assertTrue("Users not found.", users.length != 0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test003GetAllUsersAsArrayList() {
		ResponseEntity<ArrayList> response = this.restTemplate.getForEntity("/users", ArrayList.class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<User> users = response.getBody();
		Assert.assertTrue("Users not found.", !users.isEmpty());
	}

	@Test
	public void test004GetUserByID() {
		ResponseEntity<User> response = this.restTemplate.getForEntity("/users/" + idUserTest, User.class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		User user = response.getBody();
		Assert.assertTrue("User name is not equals.", user.getName().equals(nameUserTest));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest));
	}

	@Test
	public void test005GetUserByDNI() {
		ResponseEntity<User> response = this.restTemplate.getForEntity("/users/dni/" + dniUserTest, User.class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		User user = response.getBody();
		Assert.assertTrue("User name is not equals.", user.getName().equals(nameUserTest));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest));
	}
	
	@Test
	public void test006UpdateUser() {
		User userUpdated = this.restTemplate.getForEntity("/users/" + idUserTest, User.class).getBody();
		userUpdated.setName("NewUser");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		 
		HttpEntity<User> entity = new HttpEntity<User>(userUpdated, headers);
		
		ResponseEntity<User> response = this.restTemplate.exchange("/users/dni/" + dniUserTest, HttpMethod.PUT, entity, User.class);
		
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		User user = response.getBody();
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest));
		Assert.assertFalse("User name haven't updated.", user.getName().equals(nameUserTest));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
	}

	@Test
	public void test007NewUserToJSON() {
		User newUser = new User(dniUserTest + "J", "IamJSON", emailUserTest);
		JsonNode node = objectMapper.valueToTree(newUser);
		Assert.assertTrue("User DNI is null.", node.hasNonNull("dni"));
		Assert.assertTrue("User email is not equals.", node.get("email").textValue().equals(emailUserTest));
		
		ResponseEntity<JsonNode> response = this.restTemplate.postForEntity("/users", node, JsonNode.class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		JsonNode userJson = response.getBody();
		Assert.assertTrue("User email is not equals.", node.get("name").textValue().equals("IamJSON"));
		
		User user = objectMapper.convertValue(userJson, User.class);
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest + "J"));
		Assert.assertTrue("User name is not equals.", user.getName().equals("IamJSON"));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
	}

	@Test
	public void test008DeleteUser() {
		ResponseEntity<String> response = restTemplate.exchange("/users/dni/" + dniUserTest, HttpMethod.DELETE, null,
				String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("Not equals.", response.getBody(), "User deleted");
	}
	
	@Test
	public void test009DeleteUserByName() {
		ResponseEntity<User[]> responseGet = this.restTemplate.getForEntity("/users/name/" + "IamJSON", User[].class);
		Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseGet.getHeaders().getContentType());
		Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
		User user = responseGet.getBody()[0];
		Assert.assertTrue("User name is not equals.", user.getName().equals("IamJSON"));
		Assert.assertTrue("User email is not equals.", user.getEmail().equals(emailUserTest));
		Assert.assertTrue("User DNI is not equals.", user.getDni().equals(dniUserTest + "J"));
		
		
		ResponseEntity<String> responseDelete = restTemplate.exchange("/users/dni/" + user.getDni(), HttpMethod.DELETE, null,
				String.class);
		Assert.assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
		Assert.assertEquals("Not equals.", responseDelete.getBody(), "User deleted");
	}

}
