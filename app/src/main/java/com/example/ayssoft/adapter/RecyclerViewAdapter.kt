package com.example.ayssoft.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ayssoft.R
import com.example.ayssoft.model.ProductModel
import com.example.ayssoft.view.DetailsActivity

class RecyclerViewAdapter(private val productList:ArrayList<ProductModel>,private val listener:Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

     interface Listener{
         fun onItemClick(productModel: ProductModel)
     }
    private val colors:Array<String> =arrayOf("#1e81fb","#20124d","#FF8243","#0c343d","#38761d","#d8c3c3","#6895bd", "#6093bd","#53BC53", "#20124d","#d5a6bd","#e0b635")

    class RowHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(productModel:ProductModel,colors:Array<String>,position: Int,listener: Listener) {
           itemView.setOnClickListener{
               listener.onItemClick(productModel)
           }
            itemView.setBackgroundColor(Color.parseColor(colors[position%12]))
            val productName: TextView =itemView.findViewById(R.id.product_name)
            val productPrice: TextView =itemView.findViewById(R.id.product_price)
            val productImage: ImageView=itemView.findViewById(R.id.product_image)

            productName.text=productModel.name
            productPrice.text="${productModel.price}₺".toString()
            val imageUrl=productModel.image
            Glide.with(itemView.context).load(imageUrl).into(productImage)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
    val view =LayoutInflater.from(parent.context).inflate(R.layout.product_card,parent,false)
    return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.count()

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(productList[position],colors,position,listener)

    }


}