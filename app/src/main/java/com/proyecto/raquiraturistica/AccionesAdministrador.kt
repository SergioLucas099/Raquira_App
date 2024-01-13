package com.proyecto.raquiraturistica

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.raquiraturistica.includes.MiToolbar

class AccionesAdministrador : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance()
    private lateinit var Imagen_foto: ImageView
    private lateinit var Texto_foto: TextView
    private lateinit var Imagen_lugares: ImageView
    private lateinit var Texto_lugares: TextView
    private lateinit var Imagen_crear_usuarios: ImageView
    private lateinit var Texto_crear_usuarios: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones_administrador)

        MiToolbar.show(this, "Acciones Administrador", false)

        Imagen_foto = findViewById(R.id.img_foto)
        Texto_foto = findViewById(R.id.txt_foto)
        Imagen_lugares = findViewById(R.id.img_lugares)
        Texto_lugares = findViewById(R.id.txt_lugares)
        Imagen_crear_usuarios = findViewById(R.id.img_crear_usuarios)
        Texto_crear_usuarios = findViewById(R.id.txt_crear_usuarios)

        Imagen_foto.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, Imagenes::class.java)
            startActivity(intent)
        }

        Texto_foto.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, Imagenes::class.java)
            startActivity(intent)
        }

        Imagen_lugares.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, VerSitiosTuristicos::class.java)
            startActivity(intent)
        }

        Texto_lugares.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, VerSitiosTuristicos::class.java)
            startActivity(intent)
        }

        Imagen_crear_usuarios.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, Crear_Usuarios::class.java)
            startActivity(intent)
        }

        Texto_crear_usuarios.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, Crear_Usuarios::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Salir) {
            auth.signOut()
            val intent = Intent(this@AccionesAdministrador, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}