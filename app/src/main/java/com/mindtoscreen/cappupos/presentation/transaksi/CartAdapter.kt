package com.mindtoscreen.cappupos.presentation.transaksi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemCartBinding
import java.text.NumberFormat
import java.util.Locale

/**
 * Adapter item keranjang transaksi: nama, harga satuan, qty (+/-), subtotal.
 */
class CartAdapter(
    private val onTambah: (String?) -> Unit,
    private val onKurang: (String?) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items = emptyList<CartItem>()

    private val hargaFormat: NumberFormat = NumberFormat
        .getNumberInstance(Locale("in", "ID"))
        .apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }

    fun updateData(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.textNama.text = item.product.nama
            binding.textHarga.text = "Rp ${hargaFormat.format(item.product.harga)}"
            binding.textQty.text = item.quantity.toString()
            binding.textSubtotal.text = "Rp ${hargaFormat.format(item.product.harga * item.quantity)}"
            binding.btnMinus.setOnClickListener { onKurang(item.product.id) }
            binding.btnPlus.setOnClickListener { onTambah(item.product.id) }
        }
    }
}
