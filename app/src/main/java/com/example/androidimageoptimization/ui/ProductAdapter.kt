package com.example.androidimageoptimization.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidimageoptimization.databinding.ItemProductBinding
import com.example.androidimageoptimization.model.Product
import com.example.androidimageoptimization.R

class ProductAdapter(
    private val onItemClick: (Product) -> Unit = {},
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.binding.tvName.text = product.name
        holder.binding.tvDescription.text = product.description
        holder.binding.ivThumb.loadImage(product.thumbUrl)
        holder.binding.root.setOnClickListener { onItemClick(product) }
        holder.itemView.context.getString(R.string.product_code, product.id)
    }

    object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(old: Product, new: Product) = old.id == new.id
        override fun areContentsTheSame(old: Product, new: Product) = old == new
    }
}