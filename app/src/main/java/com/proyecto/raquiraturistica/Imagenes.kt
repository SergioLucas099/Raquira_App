package com.proyecto.raquiraturistica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.proyecto.raquiraturistica.Adapter.FotografiasAdapter
import com.proyecto.raquiraturistica.Model.FotografiasModel

class Imagenes : AppCompatActivity() {

    private lateinit var BotonAtrasImagenes: ImageView
    private lateinit var BotonAgregarImagen: ImageView
    private lateinit var TextoAgregarImagen: TextView
    private lateinit var RecyclerViewImg: RecyclerView
    private lateinit var reference: DatabaseReference
    private lateinit var lista : ArrayList <FotografiasModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagenes)

        BotonAtrasImagenes = findViewById(R.id.BotonAtrasImagenes)
        BotonAgregarImagen = findViewById(R.id.BotonAgregarImagen)
        TextoAgregarImagen = findViewById(R.id.TextoAgregarImagen)
        RecyclerViewImg = findViewById(R.id.RecyclerFotos)

        RecyclerViewImg.layoutManager = LinearLayoutManager(this)
        RecyclerViewImg.setHasFixedSize(true)
        RecyclerViewImg.visibility = View.GONE

        lista = arrayListOf<FotografiasModel>()

        reference = FirebaseDatabase.getInstance().getReference("ImagenesPromocionales")

        reference.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(FotografiasModel::class.java)
                        lista.add(data!!)
                    }
                    val Adapter = FotografiasAdapter(lista)
                    RecyclerViewImg.adapter = Adapter
                    RecyclerViewImg.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        BotonAtrasImagenes.setOnClickListener {
            val intent = Intent(this@Imagenes, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        BotonAgregarImagen.setOnClickListener {
            val intent = Intent(this@Imagenes, AgregarFotografias::class.java)
            startActivity(intent)
        }

        TextoAgregarImagen.setOnClickListener {
            val intent = Intent(this@Imagenes, AgregarFotografias::class.java)
            startActivity(intent)
        }
    }
}