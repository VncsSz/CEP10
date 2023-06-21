package com.example.cep10.ui

import androidx.lifecycle.ViewModel

class CepViewModel : ViewModel() {
    private val cepList: ArrayList<String> = ArrayList()

    fun addCep(cep: String) {
        cepList.add(cep)
    }

    fun getCepList(): ArrayList<String> {
        return cepList
    }
}
