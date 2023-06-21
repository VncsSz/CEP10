package com.example.cep10.ui.mapa

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cep10.databinding.FragmentMapaBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.cep10.ui.CepViewModel

class MapaFragment : Fragment() {

    private lateinit var binding: FragmentMapaBinding

    companion object {
        private val cepList: ArrayList<String> = ArrayList()

        fun addCep(cep: String) {
            cepList.add(cep)
        }

        fun getCepList(): ArrayList<String> {
            return cepList
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = binding.button
        val result = binding.result

        button.setOnClickListener {
            val cep = binding.CEP
            val value = cep.text.toString()
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            if (value.length < 8) {
                Snackbar.make(view, "Número menor que 8 dígitos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            } else {
                buscar(result, value)
            }
        }
    }

    private fun buscar(result: TextView, value: String) {
        Thread {
            val url = URL("https://viacep.com.br/ws/${value}/json/")
            val conn = url.openConnection() as HttpsURLConnection

            try {
                val data = conn.inputStream.bufferedReader().readText()
                val obj = JSONObject(data)

                if (obj.has("erro")) {
                    requireActivity().runOnUiThread {
                        Snackbar.make(result, "CEP inválido", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    }
                } else {
                    requireActivity().runOnUiThread {
                        result.text = ("Rua: ${obj.getString("logradouro")}\n" +
                                "Bairro: ${obj.getString("bairro")}\n" +
                                "Cidade: ${obj.getString("localidade")}\n" +
                                "Estado: ${obj.getString("uf")}\n" +
                                "DDD: ${obj.getString("ddd")}\n")
                        result.visibility = View.VISIBLE
                        val cepViewModel = ViewModelProvider(requireActivity()).get(CepViewModel::class.java)
                        cepViewModel.addCep(value)
                    }
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Snackbar.make(result, "Erro ao buscar o CEP", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show()
                }
            } finally {
                conn.disconnect()
            }
        }.start()
    }
}
