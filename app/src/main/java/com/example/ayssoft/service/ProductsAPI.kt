package com.example.ayssoft.service

import com.example.ayssoft.model.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface ProductsAPI {
// base_url https://5fc9346b2af77700165ae514.mockapi.io/products
    @GET("products")
    fun getData():Call<List<ProductModel>>
}