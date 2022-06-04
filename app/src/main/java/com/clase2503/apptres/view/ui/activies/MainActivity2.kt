package com.clase2503.apptres.view.ui.activies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.clase2503.apptres.databinding.ActivityMain2Binding
import com.clase2503.apptres.databinding.ActivityMainBinding
import com.clase2503.apptres.model.Producto
import com.clase2503.apptres.model.ProductoDetalles
import com.clase2503.apptres.model.ProductosApi
import com.clase2503.apptres.view.adapter.Adaptador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity2 : AppCompatActivity(){
    private lateinit var binding: ActivityMain2Binding

    private val BASE_URL = "https://www.serverbpw.com/"

    private val LOGTAG = "LOGS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getStringExtra("prodId")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productosApi: ProductosApi = retrofit.create(ProductosApi::class.java)

        val call: Call<ProductoDetalles> = productosApi.getProductoDetalles(id)

        call.enqueue(object: Callback<ProductoDetalles> {
            override fun onResponse(call: Call<ProductoDetalles>, response: Response<ProductoDetalles>) {
                Log.d(LOGTAG, "Respuesta del servidor: ${response.toString() }")
                Log.d(LOGTAG, "Datos:  ${response.body().toString()}")
                val item = response.body()!!

                binding.tDesc.text = item.desc
                binding.tName.text = item.name
                Glide.with(binding.root.context)
                    .load(item.imagUrl)
                    .into(binding.ivProducto)



                //val adaptador = Adaptador(this@MainActivity2, response.body()!!, this@MainActivity2)

                //val recyclerView = binding.rvMenu

                //recyclerView.layoutManager = LinearLayoutManager(this@MainActivity2)

                //recyclerView.adapter = adaptador
            }

            override fun onFailure(call: Call<ProductoDetalles>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                Toast.makeText(this@MainActivity2,"No hay conexión", Toast.LENGTH_LONG).show()
            }
        })




    }

}