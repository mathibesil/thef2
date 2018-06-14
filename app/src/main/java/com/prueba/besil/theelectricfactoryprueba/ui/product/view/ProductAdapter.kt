package com.prueba.besil.theelectricfactoryprueba.ui.product.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var isLoading: Boolean = false
    var listPedidos : List<Pedido.PedidoProduct> = listOf()
    lateinit var context: Context

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.txtName?.text = listPedidos[position].product.name
        holder.etQuantity?.setText(listPedidos[position].quantity)
        Glide.with(context)
                .load(listPedidos[position].product.img)
                .into(holder.imgProduct)
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listPedidos.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val etQuantity = itemView.findViewById<EditText>(R.id.etQuantity)
        val imgProduct = itemView.findViewById<ImageView>(R.id.imgProduct)
    }

}