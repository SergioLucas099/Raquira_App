package com.proyecto.raquiraturistica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.proyecto.raquiraturistica.Adapter.SitioAdapter
import com.proyecto.raquiraturistica.Model.SitioModel
import com.squareup.picasso.Picasso

class SitiosInteres : AppCompatActivity() {

    private lateinit var lista : ArrayList<SitioModel>
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sitios_interes)

        val Dato = findViewById<TextView>(R.id.txtDato)
        val View1 = findViewById<View>(R.id.view1)
        val View2 = findViewById<View>(R.id.view2)
        val View3 = findViewById<View>(R.id.view3)
        val View4 = findViewById<View>(R.id.view4)
        val txt1 = findViewById<TextView>(R.id.txt1)
        val txt1Ingles = findViewById<TextView>(R.id.txt1Ingles)
        val txt2 = findViewById<TextView>(R.id.txt2)
        val txt2Ingles = findViewById<TextView>(R.id.txt2Ingles)
        val txt3 = findViewById<TextView>(R.id.txt3)
        val txt3Ingles = findViewById<TextView>(R.id.txt3Ingles)
        val txt4 = findViewById<TextView>(R.id.txt4)
        val txt4Ingles = findViewById<TextView>(R.id.txt4Ingles)
        val Atras = findViewById<ImageView>(R.id.AtrasImg)
        val RecyclerSitios = findViewById<RecyclerView>(R.id.RecyclerSitios)
        var Nombre: String? = intent.getStringExtra("Dato")

        RecyclerSitios.layoutManager = LinearLayoutManager(this)
        RecyclerSitios.setHasFixedSize(true)
        lista = arrayListOf<SitioModel>()

        Atras.setOnClickListener {
            val intent = Intent(this@SitiosInteres, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        if (Nombre.equals("Parque")){
            Dato.setText("Parque")
            View1.visibility = View.VISIBLE
            txt1.visibility = View.VISIBLE
            txt1Ingles.visibility = View.VISIBLE
            reference = FirebaseDatabase.getInstance().getReference("Parque")

            var query : Query = reference.orderByChild("Puntuacion")

            query.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        var ky:String = ""
                        var itnm:String = ""
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(SitioModel::class.java)
                            lista.add(data!!)
                        }

                        lista.sortByDescending {
                            it.Puntuacion
                        }

                        lista.sortByDescending {
                            it.Puntuacion
                        }

                        val Adapter = SitioAdapter(lista)
                        RecyclerSitios.adapter = Adapter
                        RecyclerSitios.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        else if (Nombre.equals("Hotel")){
            Dato.setText("Hotel")
            View2.visibility = View.VISIBLE
            txt2.visibility = View.VISIBLE
            txt2Ingles.visibility = View.VISIBLE
            reference = FirebaseDatabase.getInstance().getReference("Hotel")
            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(SitioModel::class.java)
                            lista.add(data!!)
                        }
                        val Adapter = SitioAdapter(lista)
                        RecyclerSitios.adapter = Adapter
                        RecyclerSitios.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        else if (Nombre.equals("Restaurante")){
            Dato.setText("Restaurante")
            View3.visibility = View.VISIBLE
            txt3.visibility = View.VISIBLE
            txt3Ingles.visibility = View.VISIBLE
            reference = FirebaseDatabase.getInstance().getReference("Restaurante")
            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(SitioModel::class.java)
                            lista.add(data!!)
                        }
                        val Adapter = SitioAdapter(lista)
                        RecyclerSitios.adapter = Adapter
                        RecyclerSitios.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        else if (Nombre.equals("Tienda")){
            Dato.setText("Tienda")
            View4.visibility = View.VISIBLE
            txt4.visibility = View.VISIBLE
            txt4Ingles.visibility = View.VISIBLE
            reference = FirebaseDatabase.getInstance().getReference("Tienda")
            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(SitioModel::class.java)
                            lista.add(data!!)
                        }
                        val Adapter = SitioAdapter(lista)
                        RecyclerSitios.adapter = Adapter
                        RecyclerSitios.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}