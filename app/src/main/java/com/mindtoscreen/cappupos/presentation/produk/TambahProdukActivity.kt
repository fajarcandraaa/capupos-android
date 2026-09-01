package com.mindtoscreen.cappupos.presentation.produk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.databinding.ActivityTambahProdukBinding
import com.mindtoscreen.cappupos.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Form tambah produk.
 * FR-01.1: Sistem harus memungkinkan pengguna menambah produk
 * dengan field: nama (wajib), foto (opsional), kategori (wajib),
 * harga (wajib), deskripsi (opsional).
 */
@AndroidEntryPoint
class TambahProdukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahProdukBinding
    private val viewModel: TambahProdukViewModel by viewModels()

    // Kategori statis (TASK-004 handles kategori CRUD).
    private val kategoriList = listOf("Makanan", "Minuman", "Penyedap")
    private val kategoriIdMap = mapOf("Makanan" to "makanan", "Minuman" to "minuman", "Penyedap" to "penyedap")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupKategoriSpinner()
        setupButtons()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupKategoriSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnBatal.setOnClickListener { finish() }

        binding.btnSimpan.setOnClickListener {
            val nama = binding.editNama.text.toString().trim()
            val harga = binding.editHarga.text.toString().toDoubleOrNull() ?: 0.0
            val deskripsi = binding.editDeskripsi.text.toString().trim().ifEmpty { null }
            val kategoriNama = binding.spinnerKategori.selectedItem?.toString() ?: ""

            if (nama.isEmpty()) {
                binding.editNama.error = "Nama produk wajib diisi"
                return@setOnClickListener
            }
            if (kategoriNama.isEmpty()) {
                Toast.makeText(this, "Kategori wajib dipilih", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                nama = nama,
                kategoriId = kategoriIdMap[kategoriNama],
                harga = harga,
                deskripsi = deskripsi,
                lacakStok = false
            )

            lifecycleScope.launch {
                viewModel.simpanProduct(product).onSuccess {
                    finish()
                }.onFailure {
                    Toast.makeText(this@TambahProdukActivity, "Gagal menyimpan produk", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
