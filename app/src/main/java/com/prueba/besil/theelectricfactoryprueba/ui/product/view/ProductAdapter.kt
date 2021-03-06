package com.prueba.besil.theelectricfactoryprueba.ui.product.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    //region var
    var isLoading: Boolean = false
    var listPedidos: List<Pedido.PedidoProduct> = listOf()
    lateinit var context: Context
    lateinit var productInterface: ProductMVPView
    //endregion

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.myCustomEditTextListener.updatePosition(position) //le indico al listener de cada ítem la posición del mismo
        holder.txtName?.text = listPedidos[position].product.name
        if (listPedidos[position].quantity > 0) //solo cargo cantidades si tiene más de 0 en cantidad
            holder.etQuantity.setText(listPedidos[position].quantity.toString())
        else
            holder.etQuantity.setText("")
        Glide.with(context) //cargo imágenes en segundo plano
                .load(listPedidos[position].product.img)
                .into(holder.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false)
        context = parent.context
        return ViewHolder(v, MyCustomEditTextListener()) //instancio listener por cada ítem
    }

    override fun getItemCount(): Int {
        return listPedidos.size
    }

    class ViewHolder(itemView: View, myCustomEditTextListener: MyCustomEditTextListener) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val etQuantity = itemView.findViewById<EditText>(R.id.etQuantity)
        val imgProduct = itemView.findViewById<ImageView>(R.id.imgProduct)
        val myCustomEditTextListener: MyCustomEditTextListener = myCustomEditTextListener

        init {
            etQuantity.addTextChangedListener(myCustomEditTextListener)
        }
    }

    inner class MyCustomEditTextListener : TextWatcher {
        var position: Int = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //al cambiar cantidad en TextView, cambio cantidad en el ítem de esa posición en la lista.
            if (s!!.isNotEmpty())
                listPedidos[this.position].quantity = s.toString().toInt()
            else
                listPedidos[this.position].quantity = 0
            productInterface.totalCalc()
        }

    }

}