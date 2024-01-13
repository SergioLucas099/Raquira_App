package com.proyecto.raquiraturistica

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AgregarFotografias : AppCompatActivity() {

    private lateinit var BotonAtras: ImageView
    private lateinit var BotonSeleccionarImagen: Button
    private lateinit var BotonTomarFoto: Button
    private lateinit var Fotografia: ImageView
    private lateinit var UrlFoto: TextView
    private lateinit var SubirFoto: Button
    private lateinit var reference: DatabaseReference
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val TAG = "FirebaseStorageManager"

    companion object{
        const val REQUEST_FROM_CAMERA = 1001
        const val REQUEST_FROM_GALERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_fotografias)

        reference = FirebaseDatabase.getInstance().getReference().child("ImagenesPromocionales")
        BotonAtras = findViewById(R.id.BotonAtrasCrearFoto)
        BotonSeleccionarImagen = findViewById(R.id.BotonSelecionarImagen)
        BotonTomarFoto = findViewById(R.id.BotonTomarFotoPromocional)
        Fotografia = findViewById(R.id.Img_fotografia)
        UrlFoto = findViewById(R.id.UrlFoto)
        SubirFoto = findViewById(R.id.SubirFotoFirebase)

        BotonAtras.setOnClickListener {
            val intent = Intent(this@AgregarFotografias, Imagenes::class.java)
            startActivity(intent)
            finish()
        }

        BotonSeleccionarImagen.setOnClickListener {
            AbrirGaleria()
        }

        BotonTomarFoto.setOnClickListener {
            TomarFoto()
        }

        SubirFoto.setOnClickListener {
            SubirImagen()
        }
    }

    private fun AbrirGaleria(){
        ImagePicker.with(this).galleryOnly()
            .crop()
            .start(AgregarFotografias.REQUEST_FROM_GALERY)
    }

    private fun TomarFoto(){
        ImagePicker.with(this).cameraOnly()
            .crop()
            .start(AgregarFotografias.REQUEST_FROM_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                AgregarFotografias.REQUEST_FROM_CAMERA -> {
                    Fotografia.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                }
                AgregarFotografias.REQUEST_FROM_GALERY -> {
                    Fotografia.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
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
                UrlFoto.setText("$it")
            }.addOnFailureListener{
                Toast.makeText(this, "No se pudo cargar la Url de la imagen", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Log.e(TAG, "Carga de imagen fallida ${it.printStackTrace()}")

        }
    }

    private fun SubirImagen(){
        val Imagen = UrlFoto.text.toString()
        val Id = reference.push().key!!

        val map: MutableMap<String, Any> = HashMap()
        map["Url"] = Imagen
        map["Id"] = Id

        if (!Imagen.isEmpty()){
            reference.child(Id).setValue(map).addOnCompleteListener{
                Toast.makeText(this, "Se ha creado una imagen promocional", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Debe ingresar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
}