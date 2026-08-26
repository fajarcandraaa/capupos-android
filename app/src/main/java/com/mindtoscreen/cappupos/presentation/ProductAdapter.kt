package com.mindtoscreen.cappupos.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ItemProductBinding
import com.mindtoscreen.cappupos.domain.model.Product

class ProductAdapter(
    private val onItemClick: (Product) -> Unit = {}
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products = emptyList<Product>()

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textNama.text = product.nama
            binding.textHarga.text = "Rp \${String.format(\"%.2f\", product.harga)}"
            binding.imgFoto.setImageResource(R.drawable.ic_product)

            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }
}

