package com.mindtoscreen.cappupos.presentation.produk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.databinding.ActivityProductDetailBinding
import com.mindtoscreen.cappupos.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Detail produk: menampilkan info lengkap + ubah + hapus.
 * FR-01.2: Sistem harus memungkinkan pengguna mengubah dan menghapus produk.
 */
@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    private var currentProduct: Product? = null
    private var editMode = false

    private val kategoriList = listOf("Makanan", "Minuman", "Penyedap")
    private val kategoriIdMap = mapOf("Makanan" to "makanan", "Minuman" to "minuman", "Penyedap" to "penyedap")
    private val kategoriNameMap = mapOf("makanan" to "Makanan", "minuman" to "Minuman", "penyedap" to "Penyedap")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupKategoriSpinner()
        setupButtons()

        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: return finish()
        viewModel.loadProduct(productId)

        lifecycleScope.launch {
            viewModel.product.collect { product ->
                product?.let { showProduct(it) }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Produk"
    }

    private fun setupKategoriSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnUbah.setOnClickListener {
            if (!editMode) {
                setEditMode(true)
            } else {
                simpanUbah()
            }
        }

        binding.btnHapus.setOnClickListener {
            konfirmasiHapus()
        }
    }

    private fun showProduct(product: Product) {
        currentProduct = product
        binding.editNama.setText(product.nama)
        binding.editHarga.setText(if (product.harga == 0.0) "" else product.harga.toString())
        binding.editDeskripsi.setText(product.deskripsi ?: "")

        val kategoriNama = kategoriNameMap[product.kategoriId]
        val index = kategoriList.indexOf(kategoriNama)
        if (index >= 0) binding.spinnerKategori.setSelection(index)
    }

    private fun setEditMode(enabled: Boolean) {
        editMode = enabled
        binding.editNama.isEnabled = enabled
        binding.editHarga.isEnabled = enabled
        binding.editDeskripsi.isEnabled = enabled
        binding.spinnerKategori.isEnabled = enabled
        binding.btnUbah.text = if (enabled) "Simpan" else "Ubah"
    }

    private fun simpanUbah() {
        val product = currentProduct ?: return
        val nama = binding.editNama.text.toString().trim()
        val harga = binding.editHarga.text.toString().toDoubleOrNull() ?: product.harga
        val deskripsi = binding.editDeskripsi.text.toString().trim().ifEmpty { null }
        val kategoriNama = binding.spinnerKategori.selectedItem?.toString() ?: ""

        if (nama.isEmpty()) {
            binding.editNama.error = "Nama produk wajib diisi"
            return
        }

        val updated = product.copy(
            nama = nama,
            kategoriId = kategoriIdMap[kategoriNama] ?: product.kategoriId,
            harga = harga,
            deskripsi = deskripsi
        )

        lifecycleScope.launch {
            viewModel.ubahProduct(updated).onSuccess {
                setEditMode(false)
                Toast.makeText(this@ProductDetailActivity, "Produk berhasil diubah", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(this@ProductDetailActivity, "Gagal mengubah produk", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun konfirmasiHapus() {
        val product = currentProduct ?: return
        AlertDialog.Builder(this)
            .setTitle("Hapus Produk")
            .setMessage("Apakah Anda yakin ingin menghapus \"${product.nama}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    viewModel.hapusProduct(product.id ?: "").onSuccess {
                        finish()
                    }.onFailure {
                        Toast.makeText(this@ProductDetailActivity, "Gagal menghapus produk", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }
}
