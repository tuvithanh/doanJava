/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

/**
 *
 * @author VITHANH
 */

import com.mycompany.dao.OrderDetailDao;
import com.mycompany.model.OrderDetail;

import java.util.List;

public class OrderDetailService {
    private final OrderDetailDao orderDetailDao;

    public OrderDetailService() {
        this.orderDetailDao = new OrderDetailDao();
    }

    // Thêm một chi tiết đơn hàng
    public int addOrderDetail(OrderDetail detail) {
        return orderDetailDao.insertOrderDetail(detail);
    }

    // Thêm nhiều chi tiết đơn hàng
    public void addOrderDetails(int orderId, List<OrderDetail> details) {
        for (OrderDetail detail : details) {
            detail.setOrderId(orderId); // đảm bảo set đúng order_id
            orderDetailDao.insertOrderDetail(detail);
        }
    }

    // Lấy danh sách chi tiết đơn hàng theo orderId
    public List<OrderDetail> getDetailsByOrderId(int orderId) {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }

    // Xoá tất cả chi tiết của một đơn (nếu hủy đơn hàng)
    public void deleteDetailsByOrderId(int orderId) {
        orderDetailDao.deleteOrderDetailsByOrderId(orderId);
    }
}

