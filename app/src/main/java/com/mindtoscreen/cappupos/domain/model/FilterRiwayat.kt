package com.mindtoscreen.cappupos.domain.model

data class FilterRiwayat(
    val kategoriId: String? = null,
    val tanggalAwal: Long? = null,
    val tanggalAkhir: Long? = null,
    val metodeBayar: String? = null
)
