package com.proyecto.raquiraturistica.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.raquiraturistica.DetallesSitio
import com.proyecto.raquiraturistica.Model.SitioModel
import com.proyecto.raquiraturistica.R
import com.squareup.picasso.Picasso

class SitioAdapter(
    private val SitioLista : ArrayList<SitioModel>)
    : RecyclerView.Adapter<SitioAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ver_sitio_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lista = SitioLista[position]
        holder.Key.text = lista.Id
        holder.NombreSitio.text = lista.Nombre
        holder.DetallesSitio.text = lista.Detalles
        holder.UbicacionSitio.text = lista.Ubicacion
        holder.Puntuacion_sitio.text = lista.Puntuacion
        Picasso.get().load(lista.Imagen).into(holder.ImgSitio)

        holder.select.setOnClickListener{
            val intent = Intent(holder.select.context, DetallesSitio::class.java)
            intent.putExtra("Id", lista.Id)
            intent.putExtra("Nombre", lista.Nombre)
            intent.putExtra("Imagen", lista.Imagen)
            intent.putExtra("Detalles", lista.Detalles)
            intent.putExtra("Ubicacion", lista.Ubicacion)
            intent.putExtra("Categoria", lista.Categoria)
            intent.putExtra("Puntuacion", lista.Puntuacion)
            holder.select.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return SitioLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val Key : TextView = itemView.findViewById(R.id.Key)
        val NombreSitio : TextView = itemView.findViewById(R.id.NombreSitio)
        val ImgSitio : ImageView = itemView.findViewById(R.id.ImgSitio)
        val DetallesSitio : TextView = itemView.findViewById(R.id.DetallesSitio)
        val UbicacionSitio : TextView = itemView.findViewById(R.id.UbicacionSitio)
        val select : LinearLayout = itemView.findViewById(R.id.SeleccionarSitio)
        val Puntuacion_sitio : TextView = itemView.findViewById(R.id.Puntuacion_sitio)
        }
    }