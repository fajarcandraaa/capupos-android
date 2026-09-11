package com.mindtoscreen.cappupos.presentation.transaksi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityTransaksiBinding
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.presentation.ProductAdapter
import com.mindtoscreen.cappupos.presentation.transaksimanual.TransaksiManualActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

/**
 * Layar transaksi (tab "langsung"): pilih produk ke keranjang, atur qty,
 * Simpan/Open Bill, atau buka Transaksi Manual dan list Belum Bayar.
 * FR-04, FR-05, FR-07.
 */
@AndroidEntryPoint
class TransaksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransaksiBinding
    private val viewModel: TransaksiViewModel by viewModels()

    private val productAdapter = ProductAdapter(
        onItemClick = { product -> viewModel.tambahKeKeranjang(product) },
        onItemLongClick = { }
    )

    private val cartAdapter = CartAdapter(
        onTambah = { id -> viewModel.tambahQty(id) },
        onKurang = { id -> viewModel.kurangQty(id) }
    )

    private val hargaFormat: NumberFormat = NumberFormat
        .getNumberInstance(Locale("in", "ID"))
        .apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerViews()
        setupSearch()
        setupButtons()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.transaksi_title)
    }

    private fun setupRecyclerViews() {
        binding.recyclerProducts.apply {
            layoutManager = GridLayoutManager(this@TransaksiActivity, 2)
            adapter = productAdapter
        }
        binding.recyclerKeranjang.layoutManager = LinearLayoutManager(this)
        binding.recyclerKeranjang.adapter = cartAdapter
    }

    private fun setupSearch() {
        binding.editPencarian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupButtons() {
        binding.btnSimpanBill.setOnClickListener { viewModel.simpanBill() }
        binding.btnTransaksiManual.setOnClickListener {
            startActivity(Intent(this, TransaksiManualActivity::class.java))
        }
        binding.btnBelumBayar.setOnClickListener {
            startActivity(Intent(this, BelumBayarActivity::class.java))
        }
    }

    private fun renderKategoriChips(kategoriList: List<Kategori>) {
        val container = binding.chipContainer
        while (container.childCount > 1) {
            container.removeViewAt(1)
        }
        kategoriList.forEachIndexed { idx, kategori ->
            val chip = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    48
                ).apply { marginStart = 10 }
                text = kategori.nama
                gravity = android.view.Gravity.CENTER
                setPadding(16, 0, 16, 0)
                textSize = 12f
                setBackgroundResource(R.drawable.bg_chip_inactive)
                setTextColor(getColor(R.color.text_primary))
            }
            chip.setOnClickListener {
                viewModel.selectKategori(kategori.id)
                updateChipStyles(container, idx + 1)
            }
            container.addView(chip)
        }
        updateChipStyles(container, -1)
    }

    private fun updateChipStyles(container: LinearLayout, activeIdx: Int) {
        for (i in 0 until container.childCount) {
            val chip = container.getChildAt(i) as TextView
            if (i == activeIdx) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(getColor(R.color.surface_white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(getColor(R.color.text_primary))
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                productAdapter.updateData(state.filteredProducts)
                cartAdapter.updateData(state.keranjang)
                binding.textTotal.text = "Rp ${hargaFormat.format(state.total)}"

                val emptyCart = state.keranjang.isEmpty()
                binding.recyclerKeranjang.isVisible = !emptyCart
                binding.emptyKeranjang.isVisible = emptyCart

                if (state.kategoriList.isNotEmpty()) {
                    renderKategoriChips(state.kategoriList)
                }

                state.successMessage?.let {
                    Toast.makeText(this@TransaksiActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
                state.error?.let {
                    Toast.makeText(this@TransaksiActivity, it, Toast.LENGTH_SHORT).show()
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
