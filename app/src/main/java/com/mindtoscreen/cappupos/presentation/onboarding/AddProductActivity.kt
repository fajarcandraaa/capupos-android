package com.mindtoscreen.cappupos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase
import com.mindtoscreen.cappupos.presentation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val simpanProdukUseCase: SimpanProdukUseCase
) : ViewModel() {
    suspend fun simpanProduct(product: Product): Result<Unit> {
        return simpanProdukUseCase.execute(product)
    }
}

@AndroidEntryPoint
class AddProductActivity : ComponentActivity() {

    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        setupUI()
    }

    private fun setupUI() {
        val editNama = findViewById<EditText>(R.id.edit_nama_produk)
        val editHarga = findViewById<EditText>(R.id.edit_harga_produk)
        val editDeskripsi = findViewById<EditText>(R.id.edit_deskripsi_produk)
        val btnSimpan = findViewById<Button>(R.id.btn_simpan_produk)
        val btnBatal = findViewById<Button>(R.id.btn_batalkan_produk)
        val ivFoto = findViewById<ImageView>(R.id.iv_upload_foto)

        // Gallery/Upload foto (placeholder)
        ivFoto.setOnClickListener {
            Toast.makeText(this, "Fitur upload foto belum tersedia", Toast.LENGTH_SHORT).show()
        }

        btnSimpan.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val harga = editHarga.text.toString().toDoubleOrNull() ?: 0.0
            val deskripsi = editDeskripsi.text.toString().trim().ifEmpty { null }

            if (nama.isEmpty()) {
                editNama.error = "Nama produk wajib diisi"
                return@setOnClickListener
            }

            val product = Product(
                id = UUID.randomUUID().toString(),
                nama = nama,
                harga = harga,
                deskripsi = deskripsi,
                lacakStok = false
            )

            lifecycleScope.launch {
                // Save product to database
                val result = viewModel.simpanProduct(product)
                if (result.isSuccess) {
                    // Navigate to Home (FR-14.3)
                    startActivity(Intent(this@AddProductActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@AddProductActivity, "Gagal menyimpan produk", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }
}
