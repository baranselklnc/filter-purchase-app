package com.example.ayssoft.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ayssoft.R
import com.example.ayssoft.adapter.SepetAdapter
import com.example.ayssoft.model.ProductModel
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val sepetList = mutableListOf<ProductModel>()

        val sepetAdapter = SepetAdapter(sepetList)

        val details_addtoCard=findViewById<Button>(R.id.details_add_button)
        val detailProductImage = findViewById<ImageView>(R.id.details_photo)
        val details_name = findViewById<TextView>(R.id.details_name)
        val details_price = findViewById<TextView>(R.id.details_price)
        val details_id = findViewById<TextView>(R.id.details_id)
        val details_desc=findViewById<TextView>(R.id.details_desc)
        val details_brand=findViewById<TextView>(R.id.details_brand)
        val details_model = findViewById<TextView>(R.id.details_model)
        val details_created=findViewById<TextView>(R.id.details_created)

          val productModel = intent.getParcelableExtra<ProductModel>("productModel") as? ProductModel

        if (productModel != null) {
            details_name.text = productModel.name
            details_price.text = "${productModel.price}₺".toString()
            details_id.text = productModel.id.toString()
            details_created.text = productModel.createdAt
            details_brand.text = productModel.brand
            details_desc.text = productModel.description
            details_model.text=productModel.model
            Glide.with(this).load(productModel.image).into(detailProductImage)
        }

        details_addtoCard.setOnClickListener {
            if (productModel != null) {
                sepetAdapter.addProduct(productModel)
                sepetAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CardActivity::class.java)
                val sepetJson=Gson().toJson(sepetList)
                intent.putExtra("sepetList", sepetJson)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "ProductModel boş dönüyor", Toast.LENGTH_SHORT).show()

            }


        }
    }
}
