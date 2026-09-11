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
import com.mindtoscreen.cappupos.domain.model.Kategori
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

    private val kategoriAdapter by lazy {
        object : ArrayAdapter<Kategori>(this, android.R.layout.simple_spinner_item) {
            init {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                (view as android.widget.TextView).text = getItem(position)?.nama ?: ""
                return view
            }

            override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as android.widget.TextView).text = getItem(position)?.nama ?: ""
                return view
            }
        }
    }

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
        observeKategori()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupKategoriSpinner() {
        binding.spinnerKategori.adapter = kategoriAdapter
    }

    /**
     * Kategori dinamis dari DB (CategoryRepository via ViewModel), menggantikan
     * KategoriConstants. TASK-008.
     */
    private fun observeKategori() {
        lifecycleScope.launch {
            viewModel.kategoriList.collect { kategoriList ->
                kategoriAdapter.clear()
                kategoriAdapter.addAll(kategoriList)
            }
        }
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
            val kategoriTerpilih = binding.spinnerKategori.selectedItem as? Kategori

            if (nama.isEmpty()) {
                binding.editNama.error = getString(R.string.error_nama_wajib)
                return@setOnClickListener
            }
            if (kategoriTerpilih == null) {
                Toast.makeText(this, getString(R.string.error_kategori_wajib), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                nama = nama,
                foto = selectedFotoUri?.toString(),
                kategoriId = kategoriTerpilih.id,
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
