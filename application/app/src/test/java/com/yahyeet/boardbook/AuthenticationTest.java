package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.mock.service.MockAuthService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthenticationTest {
	List<MockAuthService.AuthenticationUser> authenticationUsers;
	MockAuthService mockAuthService;
	AuthHandler authHandler;

	@Before
	public void initTest() {
		authenticationUsers = new ArrayList<>();
		mockAuthService = new MockAuthService(authenticationUsers);
		authHandler = new AuthHandler(mockAuthService);
	}

	@After
	public void cleanTest() {
		authenticationUsers = null;
	}

	@Test
	public void login() {
		authenticationUsers.add(new MockAuthService.AuthenticationUser("test@test.test", "MyPassword", new User("Testificate")));

		try {
			authHandler.login("test@test.test", "MyPassword").thenAccept(user -> {
				Assert.assertEquals("Testificate", user.getName());
			}).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}
		Assert.assertEquals("Testificate", authHandler.getLoggedInUser().getName());
	}

	@Test
	public void register() {
		try {
			authHandler.signup("prov@prov.prov", "MinKod", "Testare").thenAccept(user -> {
				Assert.assertNotEquals(0, authenticationUsers.size());
				Assert.assertEquals("Testare", authenticationUsers.get(0).user.getName());
				Assert.assertEquals("MinKod", authenticationUsers.get(0).password);
			}).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}
	}

	@Test
	public void logout() {
		List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();

		authHandler.setLoggedInUser(new User());
		Assert.assertNotEquals(null, authHandler.getLoggedInUser());
		try {
			authHandler.logout().thenAccept(v -> Assert.assertEquals(null, authHandler.getLoggedInUser())).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}
	}
}
