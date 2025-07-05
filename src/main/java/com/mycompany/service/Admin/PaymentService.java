/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.Admin;

/**
 *
 * @author VITHANH
 */

import com.mycompany.dao.PaymentDao;
import com.mycompany.model.Payment;

import java.util.List;

public class PaymentService {
    private final PaymentDao paymentDao;

    public PaymentService() {
        this.paymentDao = new PaymentDao();
    }

    // Thêm thanh toán mới
    public int addPayment(Payment payment) {
        return paymentDao.insertPayment(payment);
    }

    // Lấy thông tin thanh toán theo order_id
    public Payment getPaymentByOrderId(int orderId) {
        return paymentDao.getPaymentByOrderId(orderId);
    }

    // Lấy toàn bộ thanh toán (cho admin)
    public List<Payment> getAllPayments() {
        return paymentDao.getAllPayments();
    }

    // Cập nhật trạng thái thanh toán (nếu hủy, hoàn tiền, vv.)
    public boolean updateStatus(int paymentId, String status) {
        return paymentDao.updatePaymentStatus(paymentId, status);
    }
}

