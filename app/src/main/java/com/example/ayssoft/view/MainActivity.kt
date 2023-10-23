package com.example.ayssoft.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.widget.SearchView

import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ayssoft.R
import com.example.ayssoft.adapter.RecyclerViewAdapter

import com.example.ayssoft.model.ProductModel
import com.example.ayssoft.service.ProductsAPI
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    private lateinit var brandAdapter: ArrayAdapter<String>
    private lateinit var modelAdapter: ArrayAdapter<String>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filterButton:ImageButton=findViewById(R.id.filterButton)
        searchView=findViewById(R.id.searchView)
        val recycler_view:RecyclerView=findViewById(R.id.recycler_view)
        recycler_view.setHasFixedSize(true)
        val layoutManager:RecyclerView.LayoutManager= LinearLayoutManager(this)
        recycler_view.layoutManager=GridLayoutManager(this,3)
//        val brandAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
//        val modelAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)

    loadData() //alta alınabilir

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
            return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               filterList(newText)
                return true
            }


        })



       //loadData() buraya gelebilir
        filterButton.setOnClickListener {
            showDialog(brandAdapter, modelAdapter)
        }



    }
    private fun filterProducts(selectedBrand: String, selectedModel: String, budget: Double?): ArrayList<ProductModel> {
        val filteredList = ArrayList<ProductModel>()

        for (product in productModels.orEmpty()) {
            // Marka ve model filtrelemesi
            if (selectedBrand.isNotEmpty() && selectedBrand != "Tümü" && product.brand != selectedBrand) {
                continue
            }
            if (selectedModel.isNotEmpty() && selectedModel != "Tümü" && product.model != selectedModel) {
                continue
            }

            val price=product.price
            // Bütçe filtrelemesi
            if (budget != null && price > budget) {
                continue
            }

            filteredList.add(product)
        }

        return filteredList
    }
    private fun showDialog(brandAdapter:ArrayAdapter<String>,modelAdapter:ArrayAdapter<String>){
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.filter_page)
        val spinBrand=dialog.findViewById<Spinner>(R.id.spinnerBrand)
        val spinModel=dialog.findViewById<Spinner>(R.id.spinnerModel)
        val priceFilterEditText=dialog.findViewById<EditText>(R.id.priceFiltterEditText)
        val filterApplyButton=dialog.findViewById<Button>(R.id.filterApplyButton)

        spinBrand?.adapter=brandAdapter
        spinModel?.adapter = modelAdapter


        filterApplyButton?.setOnClickListener {
            val selectedBrand = spinBrand?.selectedItem.toString()
            val selectedModel = spinModel?.selectedItem.toString()
            val budget = priceFilterEditText?.text.toString().toDoubleOrNull()

            // Filtreleme işlemini gerçekleştir
            val filteredProducts = filterProducts(selectedBrand, selectedModel, budget)

            // Filtrelenen ürünleri RecyclerView'da göster
            recyclerViewAdapter?.setFilteredList(filteredProducts)

            dialog.dismiss()
        }

        dialog.show()
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
                            val brands = it.map { product -> product.brand }.distinct().toMutableList()
                            val models = it.map { product -> product.model }.distinct().toMutableList()

                            brands.add(0, "Tümü")
                            models.add(0, "Tümü")

                            brandAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, brands)
                            modelAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, models)

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

        // Details sayfasına gidecek
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("productModel", productModel)
        startActivity(intent)
    }

}