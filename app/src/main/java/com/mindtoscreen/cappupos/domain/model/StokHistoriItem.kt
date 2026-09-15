package com.mindtoscreen.cappupos.domain.model

data class StokHistoriItem(
    val id: String,
    val productId: String,
    val productName: String,
    val quantityBefore: Int,
    val quantityAfter: Int,
    val reason: String,
    val timestamp: Long
)
