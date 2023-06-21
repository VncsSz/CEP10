package com.example.cep10.ui.mapa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cep10.R

class CepAdapter : ListAdapter<String, CepAdapter.CepViewHolder>(CepDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cep, parent, false)
        return CepViewHolder(view)
    }

    override fun onBindViewHolder(holder: CepViewHolder, position: Int) {
        val cep = getItem(position)
        holder.bind(cep)
    }

    class CepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cepTextView: TextView = itemView.findViewById(R.id.cepTextView)

        fun bind(cep: String) {
            cepTextView.text = cep
        }
    }

    private class CepDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
