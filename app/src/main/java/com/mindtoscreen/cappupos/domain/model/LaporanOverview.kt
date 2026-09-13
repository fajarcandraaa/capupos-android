package com.mindtoscreen.cappupos.domain.model

data class LaporanOverview(
    val periodAwal: Long,
    val periodAkhir: Long,
    val totalPenjualan: Double,
    val jumlahTransaksi: Int,
    val metodeBayarTerpopuler: String?,
    val trendPerHari: List<DailyTrend>
)

data class DailyTrend(
    val tanggal: Long,
    val totalPenjualan: Double
)
