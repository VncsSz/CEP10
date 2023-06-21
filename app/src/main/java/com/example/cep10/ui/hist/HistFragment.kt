package com.example.cep10.ui.hist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cep10.databinding.FragmentHistBinding
import com.example.cep10.ui.CepViewModel
import com.example.cep10.ui.mapa.MapaFragment
import com.example.cep10.ui.mapa.CepAdapter

class HistFragment : Fragment() {
    private lateinit var binding: FragmentHistBinding
    private lateinit var cepAdapter: CepAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cepViewModel = ViewModelProvider(requireActivity()).get(CepViewModel::class.java)
        val cepList = cepViewModel.getCepList()

        // Inicializar o adaptador com a lista de CEPs
        cepAdapter = CepAdapter()
        cepAdapter.submitList(cepList)

        // Configurar o RecyclerView com o adaptador e o layout manager
        binding.cepRecyclerView.apply {
            adapter = cepAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

