package com.mindtoscreen.cappupos.presentation.transaksimanual

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityTransaksiManualBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar transaksi manual: input nominal + deskripsi bebas per item (FR-04).
 */
@AndroidEntryPoint
class TransaksiManualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransaksiManualBinding
    private val viewModel: TransaksiManualViewModel by viewModels()

    private val adapter = TransaksiManualAdapter(
        onNominalChanged = { index, value -> viewModel.updateNominal(index, value) },
        onDeskripsiChanged = { index, value -> viewModel.updateDeskripsi(index, value) },
        onRemove = { index -> viewModel.removeItem(index) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        binding.btnTambahItem.setOnClickListener { viewModel.addItem() }
        binding.btnSimpan.setOnClickListener { viewModel.simpan() }

        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.transaksi_manual_title)
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                adapter.updateData(state.items)
                state.successMessage?.let {
                    Toast.makeText(this@TransaksiManualActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                    finish()
                }
                state.error?.let {
                    Toast.makeText(this@TransaksiManualActivity, it, Toast.LENGTH_SHORT).show()
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
