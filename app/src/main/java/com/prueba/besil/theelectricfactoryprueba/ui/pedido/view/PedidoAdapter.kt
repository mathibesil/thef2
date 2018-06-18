package com.prueba.besil.theelectricfactoryprueba.ui.pedido.view

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import java.text.SimpleDateFormat

class PedidoAdapter(var pedidoList: MutableList<Pedido>) : RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {
    var isLoading: Boolean = false
    lateinit var pedidoInterface: PedidoMVPView//se utiliza interfaz de fragmento pedidos para capturar el evento
                                               //al seleccionar uno.

    override fun onBindViewHolder(holder: PedidoAdapter.ViewHolder, position: Int) {
        //por cada ítem de la lista se le asigna texto y el listener onclick
        holder.container.setOnClickListener(View.OnClickListener {
            pedidoInterface.itemClicked(pedidoList[position],pedidoList[position].client)
        })
        holder.txtName?.text = pedidoList[position].client.name
        //muestro la fecha con un formato determinado
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = df.format(pedidoList[position].date)
        holder.txtDirection?.text = formattedDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.client_item_layout, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //se enlaza variables con parte visual de cada ítem.
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtDirection = itemView.findViewById<TextView>(R.id.txtQuantity)
        val container = itemView.findViewById<ConstraintLayout>(R.id.client_item_container)
    }
}