package com.example.ayssoft.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayssoft.R
import com.example.ayssoft.adapter.SepetAdapter
import com.example.ayssoft.model.ProductModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CardActivity : AppCompatActivity() {
    val sepetList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        val sepetJson = intent.getStringExtra("sepetList")
        val sepetListType = object : TypeToken<List<ProductModel>>() {}.type
        val sepetList = Gson().fromJson<List<ProductModel>>(sepetJson, sepetListType)

        val recyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val sepetAdapter = SepetAdapter(sepetList as MutableList<ProductModel>) // sepetteki ürünlerin listesi burada olmalı
        recyclerView.adapter = sepetAdapter


    }
}