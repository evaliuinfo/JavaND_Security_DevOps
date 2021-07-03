package com.example.demo.controllers;

import com.example.demo.TestUtil;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCartController {
    
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    public TestCartController() {}

    @Before
    public void setUp() {
        cartController = new CartController(null, null, null);
        TestUtil.injectObjects(cartController, "userRepository", userRepository);
        TestUtil.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtil.injectObjects(cartController, "itemRepository", itemRepository);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        BigDecimal price = BigDecimal.valueOf(2.99);
        item.setPrice(price);
        item.setDescription("A widget that is round");
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));
    }

    @Test
    public void addToCartPath() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setUsername("test");
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(2.99), cart.getTotal());
    }

    @Test
    public void addToCartInvalidUser() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setUsername("mary");
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void addToCartInvalidItem() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(2L);
        request.setQuantity(1);
        request.setUsername("test");
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCartPath() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(2);
        request.setUsername("test");
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setUsername("test");
        response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(2.99), cart.getTotal());
    }

    @Test
    public void removeFromCartInvalidUser() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(1);
        request.setUsername("mary");
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCartInvalidItem() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(2L);
        request.setQuantity(1);
        request.setUsername("test");
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

}
