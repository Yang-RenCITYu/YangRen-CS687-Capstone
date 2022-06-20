package com.lll.store.mapper;

import com.lll.store.entity.Order;
import com.lll.store.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insert(){
        Order order = new Order();
        order.setUid(10);
        order.setRecvName("小武");
        order.setRecvPhone("18533604224");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(10);
        orderItem.setPid(10000003);
        orderItem.setTitle("llll");
        orderMapper.insertOrderItem(orderItem);
    }
}
