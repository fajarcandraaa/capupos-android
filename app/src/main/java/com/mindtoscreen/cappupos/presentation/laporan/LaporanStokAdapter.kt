package com.mindtoscreen.cappupos.presentation.laporan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemLaporanStokBinding
import com.mindtoscreen.cappupos.domain.model.StokHistoriItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LaporanStokAdapter : RecyclerView.Adapter<LaporanStokAdapter.ViewHolder>() {

    private val items = mutableListOf<StokHistoriItem>()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("in", "ID"))

    fun updateData(newItems: List<StokHistoriItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLaporanStokBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemLaporanStokBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StokHistoriItem) {
            binding.textProduk.text = item.productName
            binding.textWaktu.text = dateFormat.format(Date(item.timestamp))
            val delta = item.quantityAfter - item.quantityBefore
            binding.textPerubahan.text = if (delta >= 0) "+$delta" else "$delta"
        }
    }
}
