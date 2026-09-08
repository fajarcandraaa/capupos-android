package com.mindtoscreen.cappupos.presentation.stok

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.databinding.ActivityAturStokBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar atur stok per produk.
 * FR-03: Manajemen Stok — aktifkan/nonaktifkan tracking, set minimal, update jumlah.
 */
@AndroidEntryPoint
class AturStokActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAturStokBinding
    private val viewModel: AturStokViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturStokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupUI()
        observeState()

        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: return finish()
        viewModel.loadProduct(productId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.atur_stok_title)
    }

    private fun setupUI() {
        binding.switchLacakStok.setOnCheckedChangeListener { _, isChecked ->
            binding.groupStokFields.isVisible = isChecked
        }

        binding.btnSimpan.setOnClickListener {
            simpanStok()
        }
    }

    private fun simpanStok() {
        val lacakStok = binding.switchLacakStok.isChecked
        val jumlahStok = binding.editJumlahStok.text.toString().toIntOrNull()
        val stokMinimal = binding.editStokMinimal.text.toString().toIntOrNull()

        if (lacakStok) {
            if (jumlahStok == null) {
                binding.editJumlahStok.error = getString(R.string.error_jumlah_stok_wajib)
                return
            }
            if (stokMinimal == null) {
                binding.editStokMinimal.error = getString(R.string.error_stok_minimal_wajib)
                return
            }
        }

        viewModel.updateStok(lacakStok, jumlahStok, stokMinimal)
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.contentScroll.isVisible = !state.loading && state.product != null
                binding.emptyState.isVisible = !state.loading && state.product == null

                state.product?.let { product ->
                    binding.textNamaProduk.text = product.nama
                    binding.switchLacakStok.isChecked = product.lacakStok
                    binding.editJumlahStok.setText(product.jumlahStok?.toString() ?: "")
                    binding.editStokMinimal.setText(product.stokMinimal?.toString() ?: "")
                    binding.groupStokFields.isVisible = product.lacakStok
                }

                state.successMessage?.let {
                    Toast.makeText(this@AturStokActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                    finish()
                }

                state.error?.let {
                    Toast.makeText(this@AturStokActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }
}
