package com.proyecto.raquiraturistica.Adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.raquiraturistica.DetallesFotografia
import com.proyecto.raquiraturistica.Model.ComentariosModel
import com.proyecto.raquiraturistica.R
import com.squareup.picasso.Picasso

class ComentariosAdapter (
    private val ComentariosLista: ArrayList<ComentariosModel>)
    : RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.comentarios_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = ComentariosLista[position]

        holder.TxtContenidoItem.text = lista.Comentario
        holder.TxtAutorItem.text = lista.Autor
        holder.TxtPuntuacion.text = lista.Puntuacion
    }

    override fun getItemCount(): Int {
        return ComentariosLista.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TxtContenidoItem: TextView = itemView.findViewById(R.id.TxtContenidoItem)
        val TxtAutorItem: TextView = itemView.findViewById(R.id.TxtAutorItem)
        val TxtPuntuacion: TextView = itemView.findViewById(R.id.TxtPuntuacion)
    }
}