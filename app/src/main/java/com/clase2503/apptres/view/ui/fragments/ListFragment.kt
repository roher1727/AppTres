package com.clase2503.apptres.view.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clase2503.apptres.R
import com.clase2503.apptres.databinding.FragmentListBinding
import com.clase2503.apptres.model.Producto
import com.clase2503.apptres.model.ProductosApi
import com.clase2503.apptres.view.adapter.Adaptador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListFragment : Fragment(), Adaptador.OnItemListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productosApi: ProductosApi = retrofit.create(ProductosApi::class.java)

        val call: Call<List<Producto>> = productosApi.getProducts("cm/2022-2/products.php")

        call.enqueue(object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                Log.d(LOGTAG, "Respuesta del servidor: ${response.toString() }")
                Log.d(LOGTAG, "Datos:  ${response.body().toString()}")

                binding.pbConexion.visibility = View.INVISIBLE

                val adaptador = Adaptador(requireContext(), response.body()!!, this@ListFragment)

                val recyclerView = binding.rvMenu

                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                recyclerView.adapter = adaptador
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                Toast.makeText(requireContext(),"No hay conexión", Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(producto: Producto) {
        Toast.makeText(requireContext(),"Seleccionaste ${producto.name}", Toast.LENGTH_LONG).show()
        val bundle = Bundle()
        bundle.putString("prodId", "${producto.id}")
        findNavController().navigate(R.id.action_listFragment_to_detailFragment,bundle)
    }

}