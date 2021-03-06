package com.prueba.besil.theelectricfactoryprueba.ui.client.view

import android.support.constraint.ConstraintLayout
import android.support.constraint.Constraints
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO

class ClientAdapter(var clientDTOList: List<ClientDTO>) : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {
    var isLoading: Boolean = false
    lateinit var clientInterface: ClientMVPView //se utiliza interfaz de fragmento cliente para capturar el evento
                                                //al seleccionar uno.

    override fun onBindViewHolder(holder: ClientAdapter.ViewHolder, position: Int) {
        //por cada ítem de la lista se le asigna texto y el listener onclick
        holder.container.setOnClickListener(View.OnClickListener {
            clientInterface.itemClicked(clientDTOList[position])
        })
        holder.txtName?.text = clientDTOList[position].name
        holder.txtDirection?.text = clientDTOList[position].direction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.client_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return clientDTOList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //se enlaza variables con parte visual de cada ítem.
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtDirection = itemView.findViewById<TextView>(R.id.txtQuantity)
        val container = itemView.findViewById<ConstraintLayout>(R.id.client_item_container)
    }

}