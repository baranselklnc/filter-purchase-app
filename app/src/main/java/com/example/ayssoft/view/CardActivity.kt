package com.example.ayssoft.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayssoft.R
import com.example.ayssoft.model.ProductModel
import com.yonder.basketlayout.BasketLayoutView

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val cardRecyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        cardRecyclerView.layoutManager=LinearLayoutManager(this)
        val sepetUrunleri = ArrayList<ProductModel>()

    }
}