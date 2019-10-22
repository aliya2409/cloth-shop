package com.javalab.clothshop.order;

import com.javalab.clothshop.model.Order;
import com.javalab.clothshop.model.OrderItem;
import com.javalab.clothshop.model.OrderStatus;
import com.javalab.clothshop.model.Product;
import com.javalab.clothshop.repository.OrderRepository;
import com.javalab.clothshop.repository.exception.UnavailableProductQuantityException;
import com.javalab.clothshop.service.order.OrderRemovalServiceImpl;
import com.javalab.clothshop.service.order.OrderRetrievalServiceImpl;
import com.javalab.clothshop.service.order.OrderSavingServiceImpl;
import com.javalab.clothshop.service.order.item.OrderItemServiceImpl;
import com.javalab.clothshop.service.product.ProductRetrievalServiceImpl;
import com.javalab.clothshop.service.product.ProductSavingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderServices {

    @Mock
    ProductRetrievalServiceImpl productRetrievalService;
    @Mock
    ProductSavingServiceImpl productSavingService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderItemServiceImpl orderItemService;

    @InjectMocks
    OrderSavingServiceImpl orderSavingService;
    @InjectMocks
    OrderRetrievalServiceImpl orderRetrievalService;
    @InjectMocks
    OrderRemovalServiceImpl orderRemovalService;


    private OrderItem orderItem;
    private Product databaseProduct;
    private Order order;

    @BeforeEach
    public void setup() {
        databaseProduct = Product.builder().name("Pen").quantity(100).build();
        databaseProduct.setId(1L);
        orderItem = new OrderItem();
        orderItem.setProduct(databaseProduct);
        orderItem.setQuantity(100);
        order = Order.builder()
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .item(orderItem)
                .status(OrderStatus.PLACED)
                .build();
    }

    @Test
    public void test_getById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order retrieved = orderRetrievalService.retrieveById(1L);
        assertEquals(order.getCreatedAt(), retrieved.getCreatedAt());
    }

    @Test
    public void test_getAll() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> retrieved = orderRetrievalService.retrieveAll();
        assertEquals(1, retrieved.size());
        assertEquals(orders, retrieved);
    }

    @Test
    public void test_throwsUnavailableQuantity() {
        databaseProduct.setQuantity(99);
        when(productRetrievalService.retrieveById(anyLong())).thenReturn(databaseProduct);
        assertThrows(UnavailableProductQuantityException.class, () -> orderSavingService.save(order));
    }

    @Test
    public void test_save() {
        when(productRetrievalService.retrieveById(anyLong())).thenReturn(databaseProduct);
        when(productSavingService.save(any(Product.class))).thenReturn(databaseProduct);
        when(orderItemService.save(any(OrderItem.class))).thenReturn(orderItem);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order saved = orderSavingService.save(order);
        assertEquals(order.getCreatedAt(), saved.getCreatedAt());
    }

    @Test
    public void test_delete() {
        orderRemovalService.removeById(2L);
        verify(orderRepository, times(1)).deleteById(anyLong());
    }
}
