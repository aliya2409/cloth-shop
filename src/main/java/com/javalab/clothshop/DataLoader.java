package com.javalab.clothshop;

import com.javalab.clothshop.model.*;
import com.javalab.clothshop.service.category.CategorySavingService;
import com.javalab.clothshop.service.order.OrderSavingService;
import com.javalab.clothshop.service.product.ProductRetrievalService;
import com.javalab.clothshop.service.product.ProductSavingService;
import com.javalab.clothshop.service.user.UserSavingService;
import com.javalab.clothshop.service.vendor.VendorSavingService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    UserSavingService userSavingService;
    VendorSavingService vendorSavingService;
    CategorySavingService categorySavingService;
    ProductSavingService productSavingService;
    ProductRetrievalService productRetrievalService;
    OrderSavingService orderSavingService;

    public static void main(String[] args) {
        SpringApplication.run(DataLoader.class, args);
    }

    @Override
    public void run(String... args) {
        final int count = productRetrievalService.retrieveAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        Category category = new Category();
        category.setName("Electronics");
        categorySavingService.save(category);

        Vendor vendor = new Vendor();
        vendor.setName("Guandzhou");
        vendorSavingService.save(vendor);

        Product product = Product.builder()
                .name("Mi Band 3").price(5000).quantity(2).category(category).vendor(vendor).build();
        product = productSavingService.save(product);

        User user = User.builder()
                .username("bastion")
                .firstName("Vincent")
                .lastName("Guerrero")
                .email("aliya2409@gmail.com")
                .password("$2y$12$2FCSV902ZDQpk/rp08kUXu5EgCQlXdrHrkNwEgChjUOJ3fyuZzYnq") //"password"
                .phone("+74951234567")
                .build();
        userSavingService.save(user);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);

        Order order = Order.builder()
                .complete(false)
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .user(user)
                .item(item).build();
        orderSavingService.save(order);
    }
}
