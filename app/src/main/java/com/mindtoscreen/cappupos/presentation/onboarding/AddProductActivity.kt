package com.mindtoscreen.cappupos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase
import com.mindtoscreen.cappupos.presentation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class AddProductActivity : ComponentActivity() {

    private val simpanProdukUseCase by lazy {
        SimpanProdukUseCase(
            com.mindtoscreen.cappupos.domain.repository.ProductRepositoryImpl(
                // TODO: Inject properly via Hilt
                object : com.mindtoscreen.cappupos.domain.repository.ProductRepository {
                    override suspend fun getProducts(): List<Product> = emptyList()
                    override suspend fun getProductCount(): Int = 0
                    override suspend fun insertProduct(product: Product) {}
                    override suspend fun deleteProduct(productId: String) {}
                }
            )
        )
    }

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
                // TODO: Call use case to save product
                // simpanProdukUseCase.execute(product)

                // Navigate to Home (FR-14.3)
                startActivity(Intent(this@AddProductActivity, HomeActivity::class.java))
                finish()
            }
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }
}
