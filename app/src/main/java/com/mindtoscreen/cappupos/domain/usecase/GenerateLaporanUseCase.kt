package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.LaporanOverview
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Use case untuk generate laporan penjualan. FR-09.
 * Agregat orders dengan status lunas dalam range tanggal, hitung total, jumlah
 * transaksi, metode terpopuler, dan trend per hari. Semua in-memory (POS volume kecil).
 */
class GenerateLaporanUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(tanggalAwal: Long, tanggalAkhir: Long): Result<LaporanOverview> {
        return orderRepository.getLaporanAggregat(tanggalAwal, tanggalAkhir)
    }
}
