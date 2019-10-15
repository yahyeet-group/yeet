package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.mock.service.MockAuthService;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthenticationTest {
	@Test
	public void login() {
		List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
		authenticationUsers.add(new MockAuthService.AuthenticationUser("test@test.test", "MyPassword", new User("id", "Testificate")));
		MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
		UserHandler userHandler = new UserHandler(new MockUserRepository());
		AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

		try {
			mockAuthService.login("test@test.test", "MyPassword").thenAccept(user -> {
				Assert.assertEquals("Testificate", user.getName());
				Assert.assertEquals("id", user.getId());
				Assert.assertEquals("test@test.test", user.getEmail());
			}).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}

		Assert.assertEquals("Testificate", authHandler.getLoggedInUser().getName());
	}

	@Test
	public void register() {
		List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
		MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
		UserHandler userHandler = new UserHandler(new MockUserRepository());
		AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

		try {
			mockAuthService.signup("prov@prov.prov", "MinKod", "Testare").thenAccept(user -> {
				Assert.assertNotEquals(0, authenticationUsers.size());
				Assert.assertEquals("Testare", authenticationUsers.get(0).user.getName());
				Assert.assertEquals("MinKod", authenticationUsers.get(0).password);
				Assert.assertEquals("prov@prov.prov", authenticationUsers.get(0).user.getEmail());
			}).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}
	}

	@Test
	public void logout() {
		List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
		MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
		UserHandler userHandler = new UserHandler(new MockUserRepository());
		AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

		authHandler.setLoggedInUser(new User());
		Assert.assertNotEquals(null, authHandler.getLoggedInUser());
		try {
			authHandler.logout().thenAccept(v -> Assert.assertEquals(null, authHandler.getLoggedInUser())).get();
		} catch (ExecutionException | InterruptedException e) {
			Assert.fail();
		}
	}
}
