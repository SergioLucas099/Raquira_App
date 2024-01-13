package com.proyecto.raquiraturistica.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.raquiraturistica.DetallesFotografia
import com.proyecto.raquiraturistica.EditarSitio
import com.proyecto.raquiraturistica.Model.FotografiasModel
import com.proyecto.raquiraturistica.Model.SitioModel
import com.proyecto.raquiraturistica.R
import com.squareup.picasso.Picasso

class VerSitioAdapter (
    private val Lista: ArrayList<SitioModel>)
    : RecyclerView.Adapter<VerSitioAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sitio_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = Lista[position]

        Picasso.get().load(lista.Imagen).into(holder.ImgSitio1)
        holder.Key1.text = lista.Id
        holder.NombreSitio1.text = lista.Nombre
        holder.DetallesSitio1.text = lista.Detalles
        holder.UbicacionSitio1.text = lista.Ubicacion

        holder.Seleccionar.setOnClickListener {
            val intent = Intent(holder.Seleccionar.context, EditarSitio::class.java)
            intent.putExtra("Nombre", lista.Nombre)
            intent.putExtra("Detalles", lista.Detalles)
            intent.putExtra("Ubicacion", lista.Ubicacion)
            intent.putExtra("Categoria", lista.Categoria)
            intent.putExtra("Imagen", lista.Imagen)
            intent.putExtra("Puntuacion", lista.Puntuacion)
            intent.putExtra("Id", lista.Id)
            holder.Seleccionar.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Lista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val Key1 : TextView = itemView.findViewById(R.id.Key1)
        val NombreSitio1 : TextView = itemView.findViewById(R.id.NombreSitio1)
        val ImgSitio1 : ImageView = itemView.findViewById(R.id.ImgSitio1)
        val DetallesSitio1 : TextView = itemView.findViewById(R.id.DetallesSitio1)
        val UbicacionSitio1 : TextView = itemView.findViewById(R.id.UbicacionSitio1)
        val CategoriaSitio1 : TextView = itemView.findViewById(R.id.CategoriaSitio1)
        val Seleccionar : LinearLayout = itemView.findViewById(R.id.Seleccionar)
        }
    }