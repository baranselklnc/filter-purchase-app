package com.example.ayssoft.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayssoft.R
import com.example.ayssoft.adapter.SepetAdapter
import com.example.ayssoft.model.ProductModel

class CardActivity : AppCompatActivity() {
    val sepetList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        val recyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val sepetAdapter = SepetAdapter(sepetList) // sepetteki ürünlerin listesi burada olmalı
        recyclerView.adapter = sepetAdapter


    }
}