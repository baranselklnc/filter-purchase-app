package com.example.ayssoft.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ayssoft.R
import com.example.ayssoft.model.ProductModel

class SepetAdapter(private val sepetList: MutableList<ProductModel>) : RecyclerView.Adapter<SepetAdapter.SepetViewHolder>() {

    class SepetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val increaseButton: Button = itemView.findViewById(R.id.increaseButton)
        val decreaseButton: Button = itemView.findViewById(R.id.decreaseButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sepetcard, parent, false)
        return SepetViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sepetList.size
    }

    override fun onBindViewHolder(holder: SepetViewHolder, position: Int) {
        val currentProduct = sepetList[position]
        holder.productName.text = currentProduct.name
        holder.productQuantity.text = "0"
        Glide.with(holder.itemView)
            .load(currentProduct.image)
            .into(holder.productImage)
        holder.increaseButton.setOnClickListener {
            // Artırma işlemi
            val currentQuantity = holder.productQuantity.text.toString().toInt()
            holder.productQuantity.text = (currentQuantity + 1).toString()
        }
        holder.decreaseButton.setOnClickListener {
            // Azaltma işlemi
            val currentQuantity = holder.productQuantity.text.toString().toInt()
            if (currentQuantity > 0) {
                holder.productQuantity.text = (currentQuantity - 1).toString()
            }


        }

        // Ürün eklemek için bir işlev ekleyin

        }
    fun addProduct(product: ProductModel) {
        sepetList.add(product)
        notifyDataSetChanged() // RecyclerView'ı güncelle
    }
}
