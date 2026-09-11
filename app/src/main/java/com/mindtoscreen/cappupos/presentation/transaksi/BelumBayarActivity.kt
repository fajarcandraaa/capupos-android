package com.mindtoscreen.cappupos.presentation.transaksi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityBelumBayarBinding
import com.mindtoscreen.cappupos.domain.usecase.UbahStatusPOUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar list transaksi tertunda ("Belum Bayar") per tanggal.
 * Mendukung ubah status PO 5 tahap (FR-05, FR-05.5).
 */
@AndroidEntryPoint
class BelumBayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelumBayarBinding
    private val viewModel: BelumBayarViewModel by viewModels()

    private val adapter = BelumBayarAdapter(
        onUbahStatus = { orderId -> showUbahStatusDialog(orderId) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelumBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.belum_bayar_title)
    }

    private fun showUbahStatusDialog(orderId: String) {
        val stages = UbahStatusPOUseCase.PO_STAGES.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle(R.string.ubah_status_po)
            .setItems(stages) { _, which ->
                viewModel.ubahStatusPo(orderId, stages[which])
            }
            .setNegativeButton(R.string.btn_batal, null)
            .show()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.recycler.isVisible = !state.loading && state.groups.isNotEmpty()
                binding.emptyState.isVisible = !state.loading && state.groups.isEmpty()
                adapter.updateData(state.groups)

                state.successMessage?.let {
                    Toast.makeText(this@BelumBayarActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
                state.error?.let {
                    Toast.makeText(this@BelumBayarActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
