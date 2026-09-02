package com.mindtoscreen.cappupos.presentation

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityHomeBinding
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.presentation.produk.ProductDetailActivity
import com.mindtoscreen.cappupos.presentation.produk.TambahProdukActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ProductAdapter { product -> openDetail(product) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupTabs()
        setupKategoriChips()
        setupSearch()
        setupFAB()
        setupMenuButton()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        binding.recyclerProducts.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = this@HomeActivity.adapter
        }
    }

    private fun setupTabs() {
        binding.root.findViewById<TextView>(R.id.tab_produk).setOnClickListener {
            viewModel.selectTab("produk")
            updateTabStyle(
                binding.root.findViewById(R.id.tab_produk),
                binding.root.findViewById(R.id.tab_langsung)
            )
        }
        binding.root.findViewById<TextView>(R.id.tab_langsung).setOnClickListener {
            viewModel.selectTab("langsung")
            updateTabStyle(
                binding.root.findViewById(R.id.tab_langsung),
                binding.root.findViewById(R.id.tab_produk)
            )
        }
    }

    private fun updateTabStyle(active: TextView, inactive: TextView) {
        active.setBackgroundResource(R.drawable.bg_tab_active)
        active.setTextColor(getColor(R.color.text_primary))
        inactive.background = null
        inactive.setTextColor(getColor(R.color.text_disabled))
    }

    private fun setupKategoriChips() {
        val chips = listOf(
            binding.root.findViewById<TextView>(R.id.chip_semua) to null,
            binding.root.findViewById<TextView>(R.id.chip_makanan) to "makanan",
            binding.root.findViewById<TextView>(R.id.chip_minuman) to "minuman",
            binding.root.findViewById<TextView>(R.id.chip_penyedap) to "penyedap"
        )

        chips.forEachIndexed { idx, (chip, kategoriId) ->
            chip.setOnClickListener {
                viewModel.selectKategori(kategoriId)
                updateChipStyles(chips.map { it.first }, idx)
            }
        }
    }

    private fun updateChipStyles(chips: List<TextView>, activeIdx: Int) {
        chips.forEachIndexed { idx, chip ->
            if (idx == activeIdx) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(getColor(R.color.surface_white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(getColor(R.color.text_primary))
            }
        }
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

    private fun setupFAB() {
        binding.fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahProdukActivity::class.java))
        }
    }

    private fun setupMenuButton() {
        binding.menuButton.setOnClickListener {
            // TODO: tentukan target navigasi menu kanan atas (drawer/settings).
            // Belum ada spec di Figma node Home. Placeholder agar klik terdaftar.
            Toast.makeText(this, "Menu belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                adapter.updateData(state.filteredProducts)
                binding.swipeRefresh.isRefreshing = false
                val isEmpty = state.filteredProducts.isEmpty()
                binding.recyclerProducts.isVisible = !isEmpty
                binding.emptyState.isVisible = isEmpty
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts()
    }

    private fun openDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
        startActivity(intent)
    }
}
