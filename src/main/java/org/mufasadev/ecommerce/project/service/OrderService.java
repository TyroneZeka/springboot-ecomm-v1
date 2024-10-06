package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.payload.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}