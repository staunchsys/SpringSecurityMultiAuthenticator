package com.staunchsys.multiauthenticator;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.staunchsys.multiauthenticator.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = MultipleauthenticationApplication.class)
public class MultipleauthenticationApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final String username = "staunchsys";
	private static final String password = "staunchsys@1234";

	@Test
	public void testWithInMemory() {
		ResponseEntity<String> result = makeRestCall("/api/ping", username, password);
		Assert.assertEquals(result.getStatusCodeValue(), 200);
		Assert.assertEquals(result.getBody(), "ok");
	}

	@Test
	public void createUser() {
		User user = new User();
		user.setPassword(new BCryptPasswordEncoder().encode("user1"));
		user.setUserName("user1");
		ResponseEntity<Object> result = testRestTemplate.withBasicAuth(username, password).postForEntity("/user/create",
				user, Object.class);
		Assert.assertEquals(result.getStatusCodeValue(), 201);
	}

	private ResponseEntity<String> makeRestCall(String url, String username, String password) {
		return testRestTemplate.withBasicAuth(username, password).getForEntity(url, String.class,
				Collections.emptyMap());
	}

}
