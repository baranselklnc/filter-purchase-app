package com.example.ayssoft.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView

import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayssoft.R
import com.example.ayssoft.adapter.RecyclerViewAdapter
import com.example.ayssoft.databinding.ActivityMainBinding
import com.example.ayssoft.model.ProductModel
import com.example.ayssoft.service.ProductsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private lateinit var searchView: SearchView
    private val BASE_URL="https://5fc9346b2af77700165ae514.mockapi.io/"
    private var productModels:ArrayList<ProductModel>?= null
    private var recyclerViewAdapter: RecyclerViewAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView=findViewById(R.id.searchView)
        val recycler_view:RecyclerView=findViewById(R.id.recycler_view)
        recycler_view.setHasFixedSize(true)
        val layoutManager:RecyclerView.LayoutManager= LinearLayoutManager(this)
        recycler_view.layoutManager=GridLayoutManager(this,3)
    loadData()

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
            return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               filterList(newText)
                return true
            }


        })



    }
    private fun filterList(query: String?){
        if(query!= null){
            val filteredList=ArrayList<ProductModel>()
            for(i in productModels!!){
                if (i.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))){
                filteredList.add(i)
                }

            }
            if (filteredList.isEmpty()){
                Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show()
            }
            else{
                recyclerViewAdapter?.setFilteredList(filteredList)
            }
        }

    }


    private fun loadData(){
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service=retrofit.create(ProductsAPI::class.java)
        val call=service.getData()
        call.enqueue(object :Callback<List<ProductModel>>{
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        productModels= ArrayList(it)
                        productModels?.let {
                            recyclerViewAdapter= RecyclerViewAdapter(it,this@MainActivity)
                            val recyclerView:RecyclerView=findViewById(R.id.recycler_view)
                            recyclerView.adapter=recyclerViewAdapter
                        }
                        /* Modul test için Logcat Kontrol Edildi
                        for (productModel:ProductModel in productModels!!){

                            println(productModel.model)
                            println(productModel.brand)
                        }
                        */

                    }
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    override fun onItemClick(productModel: ProductModel) {
        //Toast.makeText(this,"Clicked: ${productModel.name}",Toast.LENGTH_LONG).show()
        // Details sayfasına gidecek
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("productModel", productModel)
        startActivity(intent)
    }
}