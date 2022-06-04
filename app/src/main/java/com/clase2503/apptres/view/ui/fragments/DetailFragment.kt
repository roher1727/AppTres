package com.clase2503.apptres.view.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.clase2503.apptres.databinding.FragmentDetailBinding
import com.clase2503.apptres.model.ProductoDetalles
import com.clase2503.apptres.model.ProductosApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("prodId")

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
                binding.pbConexion.visibility = View.INVISIBLE
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
                Toast.makeText(requireContext(),"No hay conexión", Toast.LENGTH_LONG).show()
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}