package com.mindtoscreen.cappupos.presentation.transaksimanual

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemTransaksiManualBinding

/**
 * Adapter baris transaksi manual: nominal + deskripsi bebas + tombol hapus.
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

        fun bind(item: ManualItem, position: Int) {
            binding.editNominal.setText(item.nominal)
            binding.editDeskripsi.setText(item.deskripsi)

            binding.editNominal.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onNominalChanged(position, s?.toString() ?: "")
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            binding.editDeskripsi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onDeskripsiChanged(position, s?.toString() ?: "")
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            binding.btnRemove.setOnClickListener { onRemove(position) }
        }
    }
}
