package com.clase2503.apptres.view.ui.activies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clase2503.apptres.databinding.ActivityMainBinding
import com.clase2503.apptres.model.Producto
import com.clase2503.apptres.model.ProductosApi
import com.clase2503.apptres.view.adapter.Adaptador
import retrofit2.*
import java.util.ArrayList
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Adaptador.OnItemListener {

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productosApi: ProductosApi = retrofit.create(ProductosApi::class.java)

        val call: Call<List<Producto>> = productosApi.getProducts("cm/2022-2/products.php")

        call.enqueue(object: Callback<List<Producto>>{
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                Log.d(LOGTAG, "Respuesta del servidor: ${response.toString() }")
                Log.d(LOGTAG, "Datos:  ${response.body().toString()}")

                binding.pbConexion.visibility = View.INVISIBLE

                val adaptador = Adaptador(this@MainActivity, response.body()!!, this@MainActivity)

                val recyclerView = binding.rvMenu

                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                recyclerView.adapter = adaptador
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                Toast.makeText(this@MainActivity,"No hay conexión", Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onItemClick(producto: Producto) {
        Toast.makeText(this@MainActivity,"Seleccionaste ${producto.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this,MainActivity2::class.java).also{
            it.putExtra("prodId", "${producto.id}")
            startActivity(it)
        }
    }
}

