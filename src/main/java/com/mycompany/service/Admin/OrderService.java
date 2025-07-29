/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

/**
 *
 * @author VITHANH
 */


import com.mycompany.dao.OrderDao;
import com.mycompany.dao.OrderDetailDao;
import com.mycompany.dao.PaymentDao;
import com.mycompany.dao.CartDao;
import com.mycompany.model.CartItem;
import com.mycompany.model.Order;
import com.mycompany.model.OrderDetail;
import com.mycompany.model.Payment;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao = new OrderDao();
    private final OrderDetailDao orderDetailDao = new OrderDetailDao();
    private final PaymentDao paymentDao = new PaymentDao();
    private final CartDao cartDao = new CartDao();

    public boolean checkout(int userId, List<CartItem> cartItems, String paymentMethod) {
    if (cartItems == null || cartItems.isEmpty()) return false;

    try {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        // B1: Tạo Order
        Order order = new Order(userId, "Chờ xử lý", total);
        int orderId = orderDao.createOrder(order);
        if (orderId <= 0) return false;

        // B2: Tạo danh sách OrderDetail
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail(orderId, item.getProductId(), item.getQuantity(), item.getProduct().getPrice());
            orderDetailDao.insertOrderDetail(detail);
        }

        // B3: Tạo Payment
        Payment payment = new Payment(orderId, paymentMethod, total, "Đã thanh toán");
        paymentDao.insertPayment(payment);

        // ✅ B3.5: Cập nhật trạng thái đơn hàng
        orderDao.updateOrderStatus(orderId, "Đã thanh toán");

        // B4: Xoá khỏi giỏ hàng
        for (CartItem item : cartItems) {
            cartDao.removeItem(userId, item.getProductId());
        }

        return true;

    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}
}


