package com.javalab.clothshop.service.order;

import com.javalab.clothshop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderRemovalServiceImpl implements OrderRemovalService {

    private final OrderRepository orderRepository;

    @Override
    public void removeById(Long id) {
        orderRepository.deleteById(id);
    }
}
