package com.mindtoscreen.cappupos.presentation.riwayat

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityRiwayatBinding
import com.mindtoscreen.cappupos.domain.model.Order
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Layar riwayat transaksi (belum bayar + lunas) dengan filter kategori/tanggal/metode.
 * Hapus: lunas -> soft delete, belum bayar -> hard delete (FR-08).
 */
@AndroidEntryPoint
class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private val viewModel: RiwayatViewModel by viewModels()

    private val adapter = RiwayatAdapter(
        onHapus = { order -> showHapusDialog(order) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupFilters()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.riwayat_title)
    }

    private fun setupRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }

    private fun setupFilters() {
        // Spinner kategori diisi dinamis dari state.
        // Spinner metode statis.
        val metodeOptions = listOf(
            getString(R.string.filter_semua_metode),
            "tunai", "qris", "debit", "transfer"
        )
        binding.spinnerMetode.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, metodeOptions
        )
        binding.spinnerMetode.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val metode = if (position == 0) null else metodeOptions[position]
                viewModel.setFilterMetode(metode)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        binding.btnPilihTanggal.setOnClickListener { showDatePicker() }
        binding.btnResetFilter.setOnClickListener { viewModel.resetFilter() }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val awal = Calendar.getInstance().apply {
                set(year, month, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val akhir = Calendar.getInstance().apply {
                set(year, month, day, 23, 59, 59)
                set(Calendar.MILLISECOND, 999)
            }
            viewModel.setFilterTanggal(awal.timeInMillis, akhir.timeInMillis)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showHapusDialog(order: Order) {
        val message = if (order.status == "lunas") {
            getString(R.string.dialog_hapus_transaksi_message_lunas)
        } else {
            getString(R.string.dialog_hapus_transaksi_message_belum_bayar)
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_hapus_transaksi_title)
            .setMessage(message)
            .setPositiveButton(R.string.btn_hapus) { _, _ ->
                viewModel.hapusTransaksi(order.id ?: "")
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

                // Kategori spinner: isi sekali (id sebagai tag).
                if (binding.spinnerKategori.tag == null && state.kategoriList.isNotEmpty()) {
                    val options = listOf(getString(R.string.filter_semua_kategori)) +
                        state.kategoriList.map { it.nama }
                    val ids = listOf<String?>(null) + state.kategoriList.map { it.id }
                    binding.spinnerKategori.adapter = ArrayAdapter(
                        this@RiwayatActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        options
                    )
                    binding.spinnerKategori.tag = ids
                    binding.spinnerKategori.onItemSelectedListener =
                        object : android.widget.AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                                val kategoriIds = binding.spinnerKategori.tag as? List<String?>
                                viewModel.setFilterKategori(kategoriIds?.getOrNull(position))
                            }
                            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
                        }
                }

                state.successMessage?.let {
                    Toast.makeText(this@RiwayatActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
                state.error?.let {
                    Toast.makeText(this@RiwayatActivity, it, Toast.LENGTH_SHORT).show()
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
