package com.mindtoscreen.cappupos.presentation.produk

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
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
    private var selectedFotoUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedFotoUri = it
            binding.ivFoto.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupKategoriSpinner()
        setupFotoPicker()
        setupButtons()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupKategoriSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, KategoriConstants.KATEGORI_LIST)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapter
    }

    private fun setupFotoPicker() {
        binding.ivFoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun setupButtons() {
        binding.btnBatal.setOnClickListener { finish() }

        binding.btnSimpan.setOnClickListener {
            val nama = binding.editNama.text.toString().trim()
            val harga = binding.editHarga.text.toString().toDoubleOrNull() ?: 0.0
            val deskripsi = binding.editDeskripsi.text.toString().trim().ifEmpty { null }
            val kategoriNama = binding.spinnerKategori.selectedItem?.toString() ?: ""

            if (nama.isEmpty()) {
                binding.editNama.error = getString(R.string.error_nama_wajib)
                return@setOnClickListener
            }
            if (kategoriNama.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_kategori_wajib), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                nama = nama,
                foto = selectedFotoUri?.toString(),
                kategoriId = KategoriConstants.KATEGORI_ID_MAP[kategoriNama],
                harga = harga,
                deskripsi = deskripsi,
                lacakStok = false
            )

            lifecycleScope.launch {
                viewModel.simpanProduct(product).onSuccess {
                    finish()
                }.onFailure {
                    Toast.makeText(this@TambahProdukActivity, getString(R.string.msg_gagal_simpan), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
