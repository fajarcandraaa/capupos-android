package com.mindtoscreen.cappupos.presentation.transaksimanual

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemTransaksiManualBinding

/**
 * Adapter baris transaksi manual: nominal + deskripsi bebas + tombol hapus.
 *
 * TextWatcher disimpan sebagai field ViewHolder dan dilepas sebelum dipasang
 * lagi: tanpa itu watcher menumpuk tiap rebind (notifyDataSetChanged dipanggil
 * per-keystroke via observeState) dan keystroke diduplikasi. setText hanya
 * dipanggil bila isi beda, supaya kursor tidak lompat ke awal saat mengetik.
 */
class TransaksiManualAdapter(
    private val onNominalChanged: (Int, String) -> Unit,
    private val onDeskripsiChanged: (Int, String) -> Unit,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<TransaksiManualAdapter.ViewHolder>() {

    private var items = emptyList<ManualItem>()

    fun updateData(newItems: List<ManualItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransaksiManualBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemTransaksiManualBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var nominalWatcher: TextWatcher? = null
        private var deskripsiWatcher: TextWatcher? = null

        fun bind(item: ManualItem, position: Int) {
            // Lepas watcher dulu agar setText di bawah tidak memicu
            // onNominalChanged dengan posisi/isi lama.
            nominalWatcher?.let { binding.editNominal.removeTextChangedListener(it) }
            deskripsiWatcher?.let { binding.editDeskripsi.removeTextChangedListener(it) }

            if (binding.editNominal.text.toString() != item.nominal) {
                binding.editNominal.setText(item.nominal)
            }
            if (binding.editDeskripsi.text.toString() != item.deskripsi) {
                binding.editDeskripsi.setText(item.deskripsi)
            }

            nominalWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onNominalChanged(position, s?.toString() ?: "")
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            deskripsiWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onDeskripsiChanged(position, s?.toString() ?: "")
                }
                override fun afterTextChanged(s: Editable?) {}
            }

            binding.editNominal.addTextChangedListener(nominalWatcher)
            binding.editDeskripsi.addTextChangedListener(deskripsiWatcher)

            binding.btnRemove.setOnClickListener { onRemove(position) }
        }
    }
}
