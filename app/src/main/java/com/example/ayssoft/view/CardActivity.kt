package com.example.ayssoft.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ayssoft.R
import com.yonder.basketlayout.BasketLayoutView

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val basketLayoutView= findViewById<BasketLayoutView>(R.id.basketView)
        basketLayoutView.setBasketQuantity(1) // Optional | Default is 1
        basketLayoutView.setMaxQuantity(6) // Optional | Default is null
    }
}