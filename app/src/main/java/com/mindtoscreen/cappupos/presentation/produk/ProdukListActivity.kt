package com.mindtoscreen.cappupos.presentation.produk

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityProdukListBinding
import com.mindtoscreen.cappupos.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar utama manajemen produk: grid + tab kategori + search.
 * FR-01.3: Sistem harus menampilkan produk dalam grid,
 * dikelompokkan per tab kategori, dengan fitur search.
 */
@AndroidEntryPoint
class ProdukListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProdukListBinding
    private val viewModel: ProdukListViewModel by viewModels()
    private val adapter = ProdukGridAdapter { product -> openDetail(product) }

    // Kategori statis (TASK-004 handles kategori CRUD). Sesuai chip di HomeActivity.
    private val kategori = listOf(
        null to "Semua",
        "makanan" to "Makanan",
        "minuman" to "Minuman",
        "penyedap" to "Penyedap"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProdukListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupGrid()
        setupKategoriChips()
        setupSearch()
        setupFAB()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupGrid() {
        binding.produkGrid.layoutManager = GridLayoutManager(this, 2)
        binding.produkGrid.adapter = adapter
    }

    private fun setupKategoriChips() {
        binding.kategoriChips.removeAllViews()
        kategori.forEach { (id, label) ->
            val chip = TextView(this).apply {
                text = label
                textSize = 12f
                setPadding(dp(16), 0, dp(16), 0)
                gravity = Gravity.CENTER
                setOnClickListener { viewModel.selectKategori(id) }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply { marginEnd = dp(10) }
            binding.kategoriChips.addView(chip, params)
        }
    }

    private fun setupSearch() {
        binding.searchEdit.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {}
            override fun onTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {
                viewModel.onSearchQueryChanged(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun setupFAB() {
        binding.fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahProdukActivity::class.java))
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                adapter.updateData(state.filteredProducts)
                val isEmpty = state.filteredProducts.isEmpty()
                binding.produkGrid.isVisible = !isEmpty
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

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
