package com.mindtoscreen.cappupos.presentation.laporan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityLaporanStokBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar histori perubahan stok akibat transaksi lunas. FR-09.
 */
@AndroidEntryPoint
class LaporanStokActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanStokBinding
    private val viewModel: LaporanStokViewModel by viewModels()
    private val adapter = LaporanStokAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanStokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.laporan_stok_title)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.recycler.isVisible = !state.loading && state.items.isNotEmpty()
                binding.emptyState.isVisible = !state.loading && state.items.isEmpty()
                adapter.updateData(state.items)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
