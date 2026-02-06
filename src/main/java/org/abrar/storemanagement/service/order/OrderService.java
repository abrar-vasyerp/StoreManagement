package org.abrar.storemanagement.service.order;

import org.abrar.storemanagement.dto.OrderRequestDto;
import org.abrar.storemanagement.entity.Order;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional(rollbackFor = Exception.class)
    Order createOrder(OrderRequestDto dto);
}
