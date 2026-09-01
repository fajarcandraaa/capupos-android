package com.mindtoscreen.cappupos.presentation.produk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemProductBinding
import com.mindtoscreen.cappupos.domain.model.Product

/**
 * Adapter untuk grid produk di ProdukListActivity.
 */
class ProdukGridAdapter(
    private val onItemClick: (Product) -> Unit = {}
) : RecyclerView.Adapter<ProdukGridAdapter.ProductViewHolder>() {

    private var products = emptyList<Product>()

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textNama.text = product.nama
            binding.textHarga.text = "Rp ${String.format("%.0f", product.harga)}"
            binding.root.setOnClickListener { onItemClick(product) }
        }
    }
}
