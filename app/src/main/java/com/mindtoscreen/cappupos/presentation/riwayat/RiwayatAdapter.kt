package com.mindtoscreen.cappupos.presentation.riwayat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ItemRiwayatOrderBinding
import com.mindtoscreen.cappupos.domain.model.Order
import java.text.NumberFormat
import java.util.Locale

/**
 * Adapter riwayat transaksi dengan header per tanggal.
 * View type: 0 = header tanggal, 1 = baris order.
 */
class RiwayatAdapter(
    private val onHapus: (Order) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<Any>()

    private val hargaFormat: NumberFormat = NumberFormat
        .getNumberInstance(Locale("in", "ID"))
        .apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }

    fun updateData(groups: List<RiwayatGroup>) {
        val flat = mutableListOf<Any>()
        groups.forEach { group ->
            flat.add(group.tanggal)
            flat.addAll(group.orders)
        }
        items = flat
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is String) TYPE_HEADER else TYPE_ORDER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_riwayat_group_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val binding = ItemRiwayatOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            OrderViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as String)
            is OrderViewHolder -> holder.bind(items[position] as Order)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tanggal: String) {
            (itemView as TextView).text = tanggal
        }
    }

    inner class OrderViewHolder(private val binding: ItemRiwayatOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.textSubtotal.text = "Rp ${hargaFormat.format(order.subtotal)}"
            binding.textJumlahItem.text = "${order.items.size} item"
            val status = if (order.status == "lunas") {
                binding.root.context.getString(R.string.status_lunas_label)
            } else {
                binding.root.context.getString(R.string.status_belum_bayar_label)
            }
            binding.textStatus.text = status
            val metode = order.metodeBayar ?: "-"
            binding.textMetode.text = metode
            binding.btnHapus.setOnClickListener { onHapus(order) }
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ORDER = 1
    }
}
