package com.mindtoscreen.cappupos.presentation.produk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
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
        supportActionBar?.title = getString(R.string.detail_produk_title)
    }

    private fun setupKategoriSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, KategoriConstants.KATEGORI_LIST)
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

        val kategoriNama = KategoriConstants.KATEGORI_NAME_MAP[product.kategoriId]
        val index = KategoriConstants.KATEGORI_LIST.indexOf(kategoriNama)
        if (index >= 0) binding.spinnerKategori.setSelection(index)
    }

    private fun setEditMode(enabled: Boolean) {
        editMode = enabled
        binding.editNama.isEnabled = enabled
        binding.editHarga.isEnabled = enabled
        binding.editDeskripsi.isEnabled = enabled
        binding.spinnerKategori.isEnabled = enabled
        binding.btnUbah.text = if (enabled) getString(R.string.btn_simpan) else getString(R.string.btn_ubah)
    }

    private fun simpanUbah() {
        val product = currentProduct ?: return
        val nama = binding.editNama.text.toString().trim()
        val harga = binding.editHarga.text.toString().toDoubleOrNull() ?: product.harga
        val deskripsi = binding.editDeskripsi.text.toString().trim().ifEmpty { null }
        val kategoriNama = binding.spinnerKategori.selectedItem?.toString() ?: ""

        if (nama.isEmpty()) {
            binding.editNama.error = getString(R.string.error_nama_wajib)
            return
        }

        val updated = product.copy(
            nama = nama,
            kategoriId = KategoriConstants.KATEGORI_ID_MAP[kategoriNama] ?: product.kategoriId,
            harga = harga,
            deskripsi = deskripsi
        )

        lifecycleScope.launch {
            viewModel.ubahProduct(updated).onSuccess {
                setEditMode(false)
                Toast.makeText(this@ProductDetailActivity, getString(R.string.msg_ubah_sukses), Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(this@ProductDetailActivity, getString(R.string.msg_gagal_ubah), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun konfirmasiHapus() {
        val product = currentProduct ?: return
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_hapus_title)
            .setMessage(getString(R.string.dialog_hapus_message, product.nama))
            .setPositiveButton(R.string.btn_hapus) { _, _ ->
                lifecycleScope.launch {
                    viewModel.hapusProduct(product.id ?: "").onSuccess {
                        finish()
                    }.onFailure {
                        Toast.makeText(this@ProductDetailActivity, getString(R.string.msg_gagal_hapus), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton(R.string.btn_batal, null)
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
