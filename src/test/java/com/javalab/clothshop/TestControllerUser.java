package com.javalab.clothshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderStatus;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.model.User;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.user.UserRemovalService;
import com.javalab.clothshop.service.user.UserRetrievalService;
import com.javalab.clothshop.service.user.UserSavingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@MockBeans({@MockBean(UserRemovalService.class), @MockBean(UserRetrievalService.class),
        @MockBean(UserSavingService.class), @MockBean(OrderSavingService.class)})
public class TestControllerUser {

    @Autowired
    UserRetrievalService userRetrievalService;
    @Autowired
    UserRemovalService userRemovalService;
    @Autowired
    UserSavingService userSavingService;
    @Autowired
    OrderSavingService orderSavingService;

    @Autowired
    MockMvc mockMvc;
    private Order order;
    private User user;
    private Product product;
    private String json;

    @Before
    public void setup() {
        product = Product.builder()
                .name("Mi Band 3").price(5000).quantity(2).build();
        order = Order.builder()
                .item(product)
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .status(OrderStatus.PLACED).build();
        user = User.builder()
                .username("pasta")
                .firstName("Alfred")
                .lastName("Fettuccine")
                .email("pasta.@mail.com")
                .password("password")
                .phone("+79874561230")
                .order(order).build();
        user.setId(1L);
    }

    @Test
    public void whenGetUser_thenOK() throws Exception {
        when(userRetrievalService.retrieveById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void whenGetAllUsers_thenOk() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRetrievalService.retrieveAll()).thenReturn(userList);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void whenPostNewUser_thenOk() throws Exception {
        User userToSave = User.builder()
                .username("pizza")
                .firstName("Papa")
                .lastName("Johns")
                .email("pizza.@mail.com")
                .password("password")
                .phone("+77894563210")
                .order(order)
                .build();
        when(userSavingService.save(any(User.class))).thenReturn(userToSave);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        json = objectMapper.writeValueAsString(userToSave);
        mockMvc.perform((post("/users")).contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username").value(userToSave.getUsername()));
    }

    @Test
    public void whenGetOrder_thenOk() throws Exception {
        when(userRetrievalService.retrieveById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/users/1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void whenPostOrder_thenOk() throws Exception {
        Order orderToPost = Order.builder()
                .item(product)
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .status(OrderStatus.PLACED).build();
        when(orderSavingService.save(any(Order.class))).thenReturn(orderToPost);
        when(userRetrievalService.retrieveById(anyLong())).thenReturn(user);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        json = objectMapper.writeValueAsString(orderToPost);
        mockMvc.perform(post("/users/1/orders").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(orderToPost.getStatus()));
        //java.lang.AssertionError: JSON path "$.status"
        //        Expected :PLACED
        //        Actual   :PLACED
    }
}
