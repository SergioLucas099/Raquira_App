package com.proyecto.raquiraturistica

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class InicioSesion : AppCompatActivity() {

    private lateinit var Usuario: EditText
    private lateinit var Contraseña: EditText
    private lateinit var BotonInicioSesion: Button
    private lateinit var BotonVolver: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        BotonInicioSesion = findViewById(R.id.BotonInicioSesion)
        Usuario = findViewById(R.id.UsuarioEditText)
        Contraseña = findViewById(R.id.ContaseñaEditText)
        BotonVolver = findViewById(R.id.volver)

        BotonInicioSesion.setOnClickListener {
            IniciarSesion()
        }

        BotonVolver.setOnClickListener {
            val intent = Intent(this@InicioSesion, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun IniciarSesion() {

        val preferences: SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        val editor = preferences.edit()

        if (!Usuario.text.toString().equals("")){
            if(!Contraseña.text.toString().equals("")){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(Usuario.text.toString(), Contraseña.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this@InicioSesion, AccionesAdministrador::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            editor.putString("user", "Administrador")
                            editor.apply()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Este usuario no esta registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Debe ingresar la contraseña", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Debe ingresar el usuario", Toast.LENGTH_SHORT).show()
        }
    }
}