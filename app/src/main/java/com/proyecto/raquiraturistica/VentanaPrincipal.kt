package com.proyecto.raquiraturistica

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.raquiraturistica.includes.MiToolbar

class VentanaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_principal)

        MiToolbar.show(this, "Ráquira App", false)

        val imageList = ArrayList<SlideModel>()
        val ImageSlider = findViewById<ImageSlider>(R.id.image_slider)
        val ImageViewParques = findViewById<ImageView>(R.id.img_btn_parques)
        val ImageViewHoteles = findViewById<ImageView>(R.id.img_btn_hoteles)
        val ImageViewRestaurantes = findViewById<ImageView>(R.id.img_btn_restaurantes)
        val ImageViewTiendas = findViewById<ImageView>(R.id.img_btn_tiendas_boton)
        val TextViewParques = findViewById<TextView>(R.id.txt_btn_parques)
        val TextViewHoteles = findViewById<TextView>(R.id.txt_btn_hoteles)
        val TextViewRestaurantes = findViewById<TextView>(R.id.txt_btn_restaurantes)
        val TextViewTiendas = findViewById<TextView>(R.id.txt_btn_tiendas_boton)
        val TextViewParquesIngles = findViewById<TextView>(R.id.txt_btn_parques_ingles)
        val TextViewHotelesIngles = findViewById<TextView>(R.id.txt_btn_hoteles_ingles)
        val TextViewRestaurantesIngles = findViewById<TextView>(R.id.txt_btn_restaurantes_ingles)
        val TextViewTiendasIngles = findViewById<TextView>(R.id.txt_btn_tiendas_boton_ingles)

        ///////////////////Imagenes Promocionales/////////////////////////
        FirebaseDatabase.getInstance().reference.child("ImagenesPromocionales")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        imageList.add(SlideModel(data.child("Url").value.toString(), ScaleTypes.FIT))
                        ImageSlider.setImageList(imageList, ScaleTypes.FIT)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        ImageViewParques.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Parque")
            intent.putExtra("Dato_Ingles", "Park´s")
            startActivity(intent)
            finish()
        }

        ImageViewHoteles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Hotel")
            intent.putExtra("Dato_Ingles", "Hotel´s")
            startActivity(intent)
            finish()
        }

        ImageViewRestaurantes.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Restaurante")
            intent.putExtra("Dato_Ingles", "Restaurant´s")
            startActivity(intent)
            finish()
        }

        ImageViewTiendas.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Tienda")
            intent.putExtra("Dato_Ingles", "Shop´s")
            startActivity(intent)
            finish()
        }

        TextViewParques.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Parque")
            intent.putExtra("Dato_Ingles", "Park´s")
            startActivity(intent)
            finish()
        }

        TextViewHoteles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Hotel")
            intent.putExtra("Dato_Ingles", "Hotel´s")
            startActivity(intent)
            finish()
        }

        TextViewRestaurantes.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Restaurante")
            intent.putExtra("Dato_Ingles", "Restaurant´s")
            startActivity(intent)
            finish()
        }

        TextViewTiendas.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Tienda")
            intent.putExtra("Dato_Ingles", "Shop´s")
            startActivity(intent)
            finish()
        }

        TextViewParquesIngles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Parque")
            intent.putExtra("Dato_Ingles", "Park´s")
            startActivity(intent)
            finish()
        }

        TextViewHotelesIngles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Hotel")
            intent.putExtra("Dato_Ingles", "Hotel´s")
            startActivity(intent)
            finish()
        }

        TextViewRestaurantesIngles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Restaurante")
            intent.putExtra("Dato_Ingles", "Restaurant´s")
            startActivity(intent)
            finish()
        }

        TextViewTiendasIngles.setOnClickListener {
            val intent = Intent(this, SitiosInteres::class.java)
            intent.putExtra("Dato", "Tienda")
            intent.putExtra("Dato_Ingles", "Shop´s")
            startActivity(intent)
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.InicioSesion) {
            val intent = Intent(this@VentanaPrincipal, InicioSesion::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val preferences: SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            val user = preferences.getString("user", "")
            if (user.equals("Administrador")){
                val intent = Intent(this, AccionesAdministrador::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}