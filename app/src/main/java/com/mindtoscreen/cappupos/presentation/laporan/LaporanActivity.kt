package com.mindtoscreen.cappupos.presentation.laporan

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityLaporanBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Layar laporan: overview (total penjualan, jumlah transaksi, metode terpopuler)
 * + grafik tren harian. FR-09.
 */
@AndroidEntryPoint
class LaporanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaporanBinding

    private val viewModel: LaporanViewModel by viewModels()

    private val rpFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID")).apply {
        maximumFractionDigits = 0
    }
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupPeriodButtons()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.laporan_title)
    }

    private fun setupPeriodButtons() {
        val buttons = listOf(
            binding.btnPeriodeHari to { viewModel.loadHariIni() },
            binding.btnPeriodeMinggu to { viewModel.loadMingguIni() },
            binding.btnPeriodeBulan to { viewModel.loadBulanIni() }
        )
        buttons.forEach { (btn, action) ->
            btn.setOnClickListener {
                buttons.forEach { (b, _) -> highlightPeriod(b, active = false) }
                highlightPeriod(btn, active = true)
                action()
            }
        }
        highlightPeriod(binding.btnPeriodeBulan, active = true)
    }

    private fun highlightPeriod(btn: TextView, active: Boolean) {
        btn.setBackgroundResource(if (active) R.drawable.bg_chip_active else R.drawable.bg_chip_inactive)
        btn.setTextColor(getColor(if (active) R.color.surface_white else R.color.text_secondary))
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.contentPanel.isVisible = !state.loading
                binding.textError.isVisible = !state.loading && state.error != null
                state.error?.let { binding.textError.text = it }

                state.overview?.let { overview ->
                    binding.textTotalPenjualan.text = rpFormat.format(overview.totalPenjualan)
                    binding.textJumlahTransaksi.text = overview.jumlahTransaksi.toString()
                    binding.textMetodeTerpopuler.text =
                        overview.metodeBayarTerpopuler ?: "-"
                    binding.chartTren.setData(overview.trendPerHari)
                    binding.textPeriode.text = getString(
                        R.string.laporan_periode_range,
                        dateFormat.format(Date(overview.periodAwal)),
                        dateFormat.format(Date(overview.periodAkhir))
                    )
                }
            }
        }
        binding.btnLihatHistoriStok.setOnClickListener {
            startActivity(Intent(this, LaporanStokActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
