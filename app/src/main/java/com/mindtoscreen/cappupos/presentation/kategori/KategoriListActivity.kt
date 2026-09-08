package com.mindtoscreen.cappupos.presentation.kategori

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ActivityKategoriListBinding
import com.mindtoscreen.cappupos.domain.model.Kategori
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Layar kelola kategori: tambah, ubah nama, hapus, reorder drag&drop.
 * FR-02: Manajemen Kategori.
 */
@AndroidEntryPoint
class KategoriListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKategoriListBinding
    private val viewModel: KategoriListViewModel by viewModels()

    private val adapter = KategoriAdapter(
        onEdit = { kategori -> showUbahDialog(kategori) },
        onDelete = { kategori -> showHapusDialog(kategori) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKategoriListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupDragDrop()
        setupButtons()
        observeState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.kategori_title)
    }

    private fun setupRecyclerView() {
        binding.recyclerKategori.layoutManager = LinearLayoutManager(this)
        binding.recyclerKategori.adapter = adapter
    }

    private fun setupDragDrop() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            private var reorderedList: List<Kategori> = emptyList()

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.bindingAdapterPosition
                val to = target.bindingAdapterPosition
                if (from < 0 || to < 0) return false
                reorderedList = viewModel.uiState.value.kategori.toMutableList().apply {
                    val moved = removeAt(from)
                    add(to, moved)
                }
                adapter.updateData(reorderedList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                if (reorderedList.isNotEmpty()) {
                    val orderedIds = reorderedList.mapNotNull { it.id }
                    viewModel.reorderKategori(orderedIds)
                    reorderedList = emptyList()
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerKategori)
    }

    private fun setupButtons() {
        binding.fabTambahKategori.setOnClickListener {
            showTambahDialog()
        }
    }

    private fun showTambahDialog() {
        val input = android.widget.EditText(this)
        input.hint = getString(R.string.hint_nama_kategori)
        AlertDialog.Builder(this)
            .setTitle(R.string.tambah_kategori_title)
            .setView(input)
            .setPositiveButton(R.string.btn_simpan) { _, _ ->
                val nama = input.text.toString().trim()
                if (nama.isEmpty()) {
                    Toast.makeText(this, R.string.error_nama_kategori_wajib, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.tambahKategori(nama)
                }
            }
            .setNegativeButton(R.string.btn_batal, null)
            .show()
    }

    private fun showUbahDialog(kategori: Kategori) {
        val input = android.widget.EditText(this)
        input.setText(kategori.nama)
        AlertDialog.Builder(this)
            .setTitle(R.string.ubah_kategori_title)
            .setView(input)
            .setPositiveButton(R.string.btn_simpan) { _, _ ->
                val nama = input.text.toString().trim()
                if (nama.isEmpty()) {
                    Toast.makeText(this, R.string.error_nama_kategori_wajib, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.ubahKategori(kategori.copy(nama = nama))
                }
            }
            .setNegativeButton(R.string.btn_batal, null)
            .show()
    }

    private fun showHapusDialog(kategori: Kategori) {
        lifecycleScope.launch {
            val count = viewModel.countProductsByKategori(kategori.id ?: "")
            val message = if (count > 0) {
                getString(R.string.dialog_hapus_kategori_message_dengan_produk, kategori.nama, count)
            } else {
                getString(R.string.dialog_hapus_kategori_message, kategori.nama)
            }
            AlertDialog.Builder(this@KategoriListActivity)
                .setTitle(R.string.dialog_hapus_kategori_title)
                .setMessage(message)
                .setPositiveButton(R.string.btn_hapus) { _, _ ->
                    viewModel.hapusKategori(kategori.id ?: "")
                }
                .setNegativeButton(R.string.btn_batal, null)
                .show()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.recyclerKategori.isVisible = !state.loading && state.kategori.isNotEmpty()
                binding.emptyState.isVisible = !state.loading && state.kategori.isEmpty()
                adapter.updateData(state.kategori)
                state.successMessage?.let {
                    Toast.makeText(this@KategoriListActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessages()
                }
                state.error?.let {
                    Toast.makeText(this@KategoriListActivity, it, Toast.LENGTH_SHORT).show()
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
