package com.clase2503.apptres.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clase2503.apptres.databinding.ListElementBinding
import com.clase2503.apptres.model.Producto

class Adaptador(context: Context, productos: List<Producto>, onItemListener: OnItemListener): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    private val productos = productos
    private val mOnItemListener = onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adaptador.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ListElementBinding.inflate(layoutInflater)

        return ViewHolder(binding,mOnItemListener)
    }

    override fun onBindViewHolder(holder: Adaptador.ViewHolder, position: Int) {
        holder.bindData(productos[position ])
    }

    override fun getItemCount(): Int {
        return productos.size
    }


    interface OnItemListener{
        fun onItemClick(producto: Producto)
    }

    class ViewHolder(binding: ListElementBinding, onItemListener: OnItemListener):RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        private val ivThumbnail = binding.ivThumbnail
        private val tvTitulo = binding.tvTitulo
        private val context = binding.root.context
        private val onItemListener = onItemListener
        private lateinit var producto: Producto

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(item: Producto){
            tvTitulo.text = item.name
            Glide.with(context)
                .load(item.thumbnail_url)
                .into(ivThumbnail)

            producto = item
        }

        override fun onClick(v: View?) {
            onItemListener.onItemClick(producto)
        }
    }
}