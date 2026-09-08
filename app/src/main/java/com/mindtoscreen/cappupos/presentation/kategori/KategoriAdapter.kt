package com.mindtoscreen.cappupos.presentation.kategori

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindtoscreen.cappupos.databinding.ItemKategoriBinding
import com.mindtoscreen.cappupos.domain.model.Kategori

class KategoriAdapter(
    private val onEdit: (Kategori) -> Unit = {},
    private val onDelete: (Kategori) -> Unit = {}
) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    private var kategori = emptyList<Kategori>()

    fun updateData(newKategori: List<Kategori>) {
        kategori = newKategori
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val binding = ItemKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KategoriViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        holder.bind(kategori[position])
    }

    override fun getItemCount(): Int = kategori.size

    inner class KategoriViewHolder(private val binding: ItemKategoriBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Kategori) {
            binding.textNama.text = cat.nama
            binding.textUrutan.text = "Urutan: ${cat.urutan}"
            binding.btnEdit.setOnClickListener { onEdit(cat) }
            binding.btnDelete.setOnClickListener { onDelete(cat) }
        }
    }
}
