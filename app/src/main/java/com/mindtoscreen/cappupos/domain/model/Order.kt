package com.mindtoscreen.cappupos.domain.model

data class Order(
    val id: String? = null,
    val status: String = "belum_bayar",
    val statusPo: String? = null,
    val metodeBayar: String? = null,
    val subtotal: Double = 0.0,
    val nominalDiterima: Double? = null,
    val kembalian: Double? = null,
    val catatan: String? = null,
    val tanggal: Long = 0L,
    val items: List<OrderItem> = emptyList()
)
