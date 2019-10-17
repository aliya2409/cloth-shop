package com.javalab.clothshop;

import com.javalab.clothshop.model.*;
import com.javalab.clothshop.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    UserRepository userRepository;
    VendorRepository vendorRepository;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataLoader.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category category = new Category();
        category.setName("Electronics");
        categoryRepository.save(category);

        Vendor vendor = new Vendor();
        vendor.setName("Guandzhou");
        vendorRepository.save(vendor);

        Product product = Product.builder()
                .name("Mi Band 3").price(5000).quantity(2).category(category).vendor(vendor).build();
        productRepository.save(product);

        User user = User.builder()
                .username("bastion")
                .firstName("Vincent")
                .lastName("Guerrero")
                .email("vince.@mail.com")
                .password("password")
                .phone("+74951234567")
                .build();
        userRepository.save(user);

        Order order = Order.builder()
                .complete(false)
                .createdAt(LocalDateTime.now())
                .shipDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .user(user)
                .item(product).build();
        orderRepository.save(order);
    }
}
