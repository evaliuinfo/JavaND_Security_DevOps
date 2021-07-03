package com.example.demo.controllers;

import com.example.demo.TestUtil;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUserController {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    public TestUserController() {}

    @Before
    public void setUp() {
        userController = new UserController(null, null, null);
        TestUtil.injectObjects(userController, "userRepository", userRepository);
        TestUtil.injectObjects(userController, "cartRepository", cartRepository);
        TestUtil.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.findByUsername("mary")).thenReturn(null);
    }

    @Test
    public void createUserPath() {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("testPassword");
        request.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());
    }

    @Test
    public void createUserPasswordTooShort() {
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername("test");
        user.setPassword("short");
        user.setConfirmPassword("short");
        final ResponseEntity<User> response = userController.createUser(user);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void findUserByNamePath() {
        final ResponseEntity<User> response = userController.findByUserName("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("test", user.getUsername());
    }

    @Test
    public void findUserNotExist() {
        final ResponseEntity<User> response = userController.findByUserName("mary");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void findUserByIdPath() {
        final ResponseEntity<User> response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0L, user.getId());
    }

    @Test
    public void findUserByIdNotExist() {
        final ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
