package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Order

interface OrderRepository {
    suspend fun getBelumBayar(): List<Order>
    suspend fun getOrderById(orderId: String): Order?
    suspend fun saveOrder(order: Order): String
    suspend fun updateStatus(orderId: String, status: String, statusPo: String?)
}
