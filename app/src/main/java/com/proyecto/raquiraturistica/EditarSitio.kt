package com.proyecto.raquiraturistica

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class EditarSitio : AppCompatActivity() {

    private lateinit var volverbtn: ImageView
    private lateinit var ImgEditar: ImageView
    private lateinit var EditarNombre: EditText
    private lateinit var EditarUbicacion: EditText
    private lateinit var EditarDetalles: EditText
    private lateinit var eliminarsitio: ImageView
    private lateinit var ActualizarSitio: Button
    private lateinit var PuntuacionED: TextView
    private lateinit var reference1: DatabaseReference
    private lateinit var reference2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_sitio)

        volverbtn = findViewById(R.id.volverbtn)
        ImgEditar = findViewById(R.id.ImgEditar)
        EditarNombre = findViewById(R.id.EditarNombre)
        EditarUbicacion = findViewById(R.id.EditarUbicacion)
        EditarDetalles = findViewById(R.id.EditarDetalles)
        eliminarsitio = findViewById(R.id.eliminarsitio)
        ActualizarSitio = findViewById(R.id.ActualizarSitio)
        PuntuacionED = findViewById(R.id.PuntuacionED)
        reference1 = FirebaseDatabase.getInstance().getReference("SitiosRaquira")
        reference2 = FirebaseDatabase.getInstance().getReference()

        var Id: String? = intent.getStringExtra("Id")
        var Nombre: String? = intent.getStringExtra("Nombre")
        var Imagen: String? = intent.getStringExtra("Imagen")
        var Detalles: String? = intent.getStringExtra("Detalles")
        var Ubicacion: String? = intent.getStringExtra("Ubicacion")
        var Categoria: String? = intent.getStringExtra("Categoria")
        var Puntuacion: String? = intent.getStringExtra("Puntuacion")

        volverbtn.setOnClickListener {
            val intent = Intent(this@EditarSitio, VerSitiosTuristicos::class.java)
            startActivity(intent)
            finish()
        }

        Picasso.get().load(Imagen).into(ImgEditar)
        EditarNombre.setText(Nombre)
        PuntuacionED.setText(Puntuacion)
        EditarDetalles.setText(Detalles)
        EditarUbicacion.setText(Ubicacion)

        eliminarsitio.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Esta seguro que desea eliminar esta información?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                reference1.child(Id.toString()).removeValue().addOnCompleteListener{
                    reference2.child(Categoria.toString()).child(Id.toString()).removeValue()
                    Toast.makeText(this, "Se ha eliminado el sitio", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditarSitio, VerSitiosTuristicos::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Error añ eliminar la imagen", Toast.LENGTH_SHORT).show()
                }
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }

        ActualizarSitio.setOnClickListener {

            val Nombre = EditarNombre.text.toString()
            val ImagenSitio = Imagen.toString()
            val Detalles = EditarDetalles.text.toString()
            val Ubicacion = EditarUbicacion.text.toString()
            val id = Id.toString()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Esta seguro que desea actualizar esta información?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                val map: MutableMap<String, Any> = HashMap()
                map["Id"] = id
                map["Nombre"] = Nombre
                map["Imagen"] = ImagenSitio
                map["Detalles"] = Detalles
                map["Ubicacion"] = Ubicacion
                reference1.child(Id.toString()).updateChildren(map).addOnCompleteListener{
                    reference2.child(Categoria.toString()).child(Id.toString()).updateChildren(map)
                    Toast.makeText(this, "Se ha actualizado la información", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditarSitio, VerSitiosTuristicos::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Error añ eliminar la información", Toast.LENGTH_SHORT).show()
                }
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }
    }
}