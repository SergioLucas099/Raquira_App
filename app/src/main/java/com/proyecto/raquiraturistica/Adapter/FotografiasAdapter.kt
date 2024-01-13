package com.proyecto.raquiraturistica.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.raquiraturistica.DetallesFotografia
import com.proyecto.raquiraturistica.Model.FotografiasModel
import com.proyecto.raquiraturistica.R
import com.squareup.picasso.Picasso

class FotografiasAdapter (
        private val FotografiasLista: ArrayList<FotografiasModel>)
        : RecyclerView.Adapter<FotografiasAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fotografias_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = FotografiasLista[position]

        Picasso.get().load(lista.Url).into(holder.ImagenPromocional)
        holder.IdImg.text = lista.Id

        holder.ImagenPromocional.setOnClickListener {
            val intent = Intent(holder.ImagenPromocional.context, DetallesFotografia::class.java)
            intent.putExtra("Url", lista.Url)
            intent.putExtra("Id", lista.Id)
            holder.ImagenPromocional.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return FotografiasLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val ImagenPromocional : ImageView = itemView.findViewById(R.id.ImgPromoItem)
        val IdImg : TextView = itemView.findViewById(R.id.idurl)

    }
}