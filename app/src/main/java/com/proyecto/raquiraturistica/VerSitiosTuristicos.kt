package com.proyecto.raquiraturistica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.proyecto.raquiraturistica.Adapter.SitioAdapter
import com.proyecto.raquiraturistica.Adapter.VerSitioAdapter
import com.proyecto.raquiraturistica.Model.FotografiasModel
import com.proyecto.raquiraturistica.Model.SitioModel

class VerSitiosTuristicos : AppCompatActivity() {

    private lateinit var BotonAtrasVerSitio: ImageView
    private lateinit var BotonAgregarSitio: ImageView
    private lateinit var TextoAgregarSitio: TextView
    private lateinit var RecyclerLugares: RecyclerView
    private lateinit var reference: DatabaseReference
    private lateinit var lista : ArrayList <SitioModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_sitios_turisticos)

        BotonAtrasVerSitio = findViewById(R.id.BotonAtrasVerSitio)
        BotonAgregarSitio = findViewById(R.id.BotonAgregarSitio)
        TextoAgregarSitio = findViewById(R.id.TextoAgregarSitio)
        RecyclerLugares = findViewById(R.id.RecyclerLugares)
        reference = FirebaseDatabase.getInstance().getReference("SitiosRaquira")

        RecyclerLugares.layoutManager = LinearLayoutManager(this)
        RecyclerLugares.setHasFixedSize(true)
        lista = arrayListOf<SitioModel>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(SitioModel::class.java)
                        lista.add(data!!)
                    }
                    val Adapter = VerSitioAdapter(lista)
                    RecyclerLugares.adapter = Adapter
                    RecyclerLugares.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        BotonAtrasVerSitio.setOnClickListener {
            val intent = Intent(this@VerSitiosTuristicos, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        BotonAgregarSitio.setOnClickListener {
            val intent = Intent(this@VerSitiosTuristicos, CrearSitio::class.java)
            startActivity(intent)
            finish()
        }

        TextoAgregarSitio.setOnClickListener {
            val intent = Intent(this@VerSitiosTuristicos, CrearSitio::class.java)
            startActivity(intent)
            finish()
        }
    }
}