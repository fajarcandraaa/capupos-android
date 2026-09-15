package com.mindtoscreen.cappupos.domain.model

/**
 * Profil usaha (FR-10). Row tunggal (single-outlet), id selalu 1.
 * Tanpa createdAt/updatedAt — tidak ada requirement audit trail.
 */
data class Store(
    val id: Long = 1,
    val nama: String = "",
    val alamat: String = "",
    val logo: String? = null,
    val kategori: String? = null,
    val deskripsi: String? = null,
    val telepon: String? = null
)
