package com.proyecto.raquiraturistica

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Crear_Usuarios : AppCompatActivity() {

    private lateinit var BotonAtrasCrearAdministradores: ImageView
    private lateinit var Btn_Seleccionar_admin: Button
    private lateinit var Btn_tomar_admin: Button
    private lateinit var Img_foto_admin: ImageView
    private lateinit var AvisoSeleccionImagenAdmin: TextView
    private lateinit var UrlFotoAdmin: TextView
    private lateinit var EditTextNombreAdministrador: EditText
    private lateinit var EditTextCorreoAdministrador: EditText
    private lateinit var EditTextContraseñaUsuario: EditText
    private lateinit var BotonCrearAdministrador: Button
    private lateinit var reference: DatabaseReference
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "FirebaseStorageManager"

    companion object{
        const val REQUEST_FROM_CAMERA = 1001
        const val REQUEST_FROM_GALERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuarios)

        BotonAtrasCrearAdministradores = findViewById(R.id.BotonAtrasCrearAdministradores)
        Btn_Seleccionar_admin = findViewById(R.id.Btn_Seleccionar_admin)
        Btn_tomar_admin = findViewById(R.id.Btn_tomar_admin)
        Img_foto_admin = findViewById(R.id.Img_foto_admin)
        AvisoSeleccionImagenAdmin = findViewById(R.id.AvisoSeleccionImagenAdmin)
        UrlFotoAdmin = findViewById(R.id.UrlFotoAdmin)
        EditTextNombreAdministrador = findViewById(R.id.EditTextNombreAdministrador)
        EditTextCorreoAdministrador = findViewById(R.id.EditTextCorreoAdministrador)
        EditTextContraseñaUsuario = findViewById(R.id.EditTextContraseñaUsuario)
        BotonCrearAdministrador = findViewById(R.id.BotonCrearAdministrador)
        reference = FirebaseDatabase.getInstance().getReference()

        BotonAtrasCrearAdministradores.setOnClickListener {
            val intent = Intent(this, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        Btn_Seleccionar_admin.setOnClickListener {
            AbrirGaleria()
        }

        Btn_tomar_admin.setOnClickListener {
            TomarFoto()
        }

        BotonCrearAdministrador.setOnClickListener {
            CrearAdministrador()
        }
    }

    private fun AbrirGaleria(){
        ImagePicker.with(this).galleryOnly()
            .crop()
            .start(CrearSitio.REQUEST_FROM_GALERY)
    }

    private fun TomarFoto(){
        ImagePicker.with(this).cameraOnly()
            .crop()
            .start(CrearSitio.REQUEST_FROM_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                CrearSitio.REQUEST_FROM_CAMERA -> {
                    Img_foto_admin.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSeleccionImagenAdmin.visibility = View.GONE
                    AvisoSeleccionImagenAdmin.visibility = View.INVISIBLE
                }
                CrearSitio.REQUEST_FROM_GALERY -> {
                    Img_foto_admin.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSeleccionImagenAdmin.visibility = View.GONE
                    AvisoSeleccionImagenAdmin.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun uploadImage(mContext: Context, imageURI: Uri){
        val ImagenFileName = "ImagenesProductos ${System.currentTimeMillis()}"
        val uploadTask = mStorageRef.child(ImagenFileName).putFile(imageURI)
        uploadTask.addOnSuccessListener {
            Log.e(TAG, "Imagen cargada con éxito")
            val DescargarUrlImagen = mStorageRef.child(ImagenFileName).downloadUrl
            DescargarUrlImagen.addOnSuccessListener {
                UrlFotoAdmin.setText("$it")
            }.addOnFailureListener{
                Toast.makeText(this, "No se pudo cargar la Url de la imagen", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Log.e(TAG, "Carga de imagen fallida ${it.printStackTrace()}")
        }
    }

    private fun CrearAdministrador(){

        """val Id = reference.push().key!!
        val Nombre = EditTextNombreAdministrador.text.toString()
        val Contraseña = EditTextContraseñaUsuario.text.toString()
        val Correo = EditTextCorreoAdministrador.text.toString()
        val Imagen = UrlFotoAdmin.text.toString()"""

        if (EditTextCorreoAdministrador.text.isNotEmpty() &&
            EditTextContraseñaUsuario.text.isNotEmpty() &&
            EditTextNombreAdministrador.text.isNotEmpty()){
            auth.createUserWithEmailAndPassword(EditTextCorreoAdministrador.text.toString(),
            EditTextContraseñaUsuario.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Se ha creado un nuevo administrador", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error al crear el nuevo administrador", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}