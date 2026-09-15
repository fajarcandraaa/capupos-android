package com.mindtoscreen.cappupos.presentation

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityHomeBinding
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.model.Product
import androidx.appcompat.app.AlertDialog
import com.mindtoscreen.cappupos.presentation.export.ExportActivity
import com.mindtoscreen.cappupos.presentation.kategori.KategoriListActivity
import com.mindtoscreen.cappupos.presentation.laporan.LaporanActivity
import com.mindtoscreen.cappupos.presentation.produk.ProductDetailActivity
import com.mindtoscreen.cappupos.presentation.produk.TambahProdukActivity
import com.mindtoscreen.cappupos.presentation.profilusaha.ProfilUsahaActivity
import com.mindtoscreen.cappupos.presentation.riwayat.RiwayatActivity
import com.mindtoscreen.cappupos.presentation.stok.AturStokActivity
import com.mindtoscreen.cappupos.presentation.transaksi.TransaksiActivity
import com.mindtoscreen.cappupos.domain.usecase.CekReminderBackupUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var cekReminderBackupUseCase: CekReminderBackupUseCase

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ProductAdapter(
        onItemClick = { product -> openDetail(product) },
        onItemLongClick = { product -> openAturStok(product) }
    )

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
            startActivity(Intent(this, TransaksiActivity::class.java))
        }
    }

    private fun updateTabStyle(active: TextView, inactive: TextView) {
        active.setBackgroundResource(R.drawable.bg_tab_active)
        active.setTextColor(getColor(R.color.text_primary))
        inactive.background = null
        inactive.setTextColor(getColor(R.color.text_disabled))
    }

    private fun setupKategoriChips() {
        val chipSemua = binding.root.findViewById<TextView>(R.id.chip_semua)
        chipSemua.setOnClickListener {
            viewModel.selectKategori(null)
            updateChipStylesForIndex(-1)
        }
    }

    private fun renderKategoriChips(kategoriList: List<Kategori>) {
        val container = binding.root.findViewById<LinearLayout>(R.id.chip_container)
        val chipSemua = container.findViewById<TextView>(R.id.chip_semua)

        // Clear dynamic chips (keep chip_semua)
        while (container.childCount > 1) {
            container.removeViewAt(1)
        }

        // Tambah kategori chips
        kategoriList.forEachIndexed { idx, kategori ->
            val chip = createChip(kategori.nama, kategori.id)
            chip.setOnClickListener {
                viewModel.selectKategori(kategori.id)
                updateChipStylesForIndex(idx + 1)
            }
            container.addView(chip)
        }

        // Set initial style
        updateChipStylesForIndex(-1)
    }

    private fun createChip(nama: String, kategoriId: String?): TextView {
        return TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                48
            ).apply {
                marginStart = 10
            }
            text = nama
            gravity = android.view.Gravity.CENTER
            setPadding(16, 0, 16, 0)
            textSize = 12f
            setBackgroundResource(R.drawable.bg_chip_inactive)
            setTextColor(getColor(R.color.text_primary))
        }
    }

    private fun updateChipStylesForIndex(activeIdx: Int) {
        val container = binding.root.findViewById<LinearLayout>(R.id.chip_container)
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
            val menu = arrayOf(
                getString(R.string.menu_profil_pengaturan),
                getString(R.string.menu_kategori),
                getString(R.string.menu_riwayat),
                getString(R.string.menu_laporan)
            )
            AlertDialog.Builder(this)
                .setItems(menu) { _, which ->
                    val target = when (which) {
                        0 -> ProfilUsahaActivity::class.java
                        1 -> KategoriListActivity::class.java
                        2 -> RiwayatActivity::class.java
                        else -> LaporanActivity::class.java
                    }
                    startActivity(Intent(this, target))
                }
                .show()
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

                // Render kategori chips dari DB
                if (state.kategoriList.isNotEmpty()) {
                    renderKategoriChips(state.kategoriList)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload kategori juga: kategori baru dari KategoriListActivity harus
        // muncul kembali di home tanpa restart app. TASK-008 fix.
        viewModel.loadKategori()
        viewModel.loadProducts()
        cekReminderBackup()
    }

    /** FR-12: reminder backup mingguan, popup wajib dismiss via salah satu tombol. */
    private fun cekReminderBackup() {
        if (!cekReminderBackupUseCase.execute()) return
        AlertDialog.Builder(this)
            .setTitle(R.string.reminder_backup_title)
            .setMessage(R.string.reminder_backup_message)
            .setCancelable(false)
            .setPositiveButton(R.string.btn_export_sekarang) { _, _ ->
                startActivity(Intent(this, ExportActivity::class.java))
            }
            .setNegativeButton(R.string.btn_nanti_saja) { _, _ ->
                cekReminderBackupUseCase.markReminderShown()
            }
            .show()
    }

    private fun openDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
        startActivity(intent)
    }

    private fun openAturStok(product: Product) {
        val intent = Intent(this, AturStokActivity::class.java)
        intent.putExtra(AturStokActivity.EXTRA_PRODUCT_ID, product.id)
        startActivity(intent)
    }
}
