package com.proyecto.raquiraturistica

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.IOException

class CrearSitio : AppCompatActivity() {

    private lateinit var Atras: ImageView
    private lateinit var Btn_Seleccionar: Button
    private lateinit var Btn_tomar: Button
    private lateinit var Img_foto_turistica: ImageView
    private lateinit var AvisoSeleccionImagen: TextView
    private lateinit var SeleccionCategoria: TextView
    private lateinit var EditTextNombre: EditText
    private lateinit var EditTextDetalles: EditText
    private lateinit var EditTextUbicación: EditText
    private lateinit var ParqueBtn: ImageView
    private lateinit var HotelBtn: ImageView
    private lateinit var RestauranteBtn: ImageView
    private lateinit var TiendaBtn: ImageView
    private lateinit var ParqueBtnTxt: TextView
    private lateinit var HotelBtnTxt: TextView
    private lateinit var RestauranteBtnTxt: TextView
    private lateinit var TiendaBtnTxt: TextView
    private lateinit var UrlFotoSitio: TextView
    private lateinit var BotonSubirSitioFirebase: TextView
    private lateinit var reference: DatabaseReference
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val TAG = "FirebaseStorageManager"

    companion object{
        const val REQUEST_FROM_CAMERA = 1001
        const val REQUEST_FROM_GALERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sitio)

        Atras = findViewById(R.id.BotonAtrasCrearSiito)
        Btn_Seleccionar = findViewById(R.id.Btn_Seleccionar)
        Btn_tomar = findViewById(R.id.Btn_tomar)
        Img_foto_turistica = findViewById(R.id.Img_foto_turistica)
        AvisoSeleccionImagen = findViewById(R.id.AvisoSeleccionImagen)
        SeleccionCategoria = findViewById(R.id.SeleccionCategoria)
        EditTextNombre = findViewById(R.id.EditTextNombre)
        EditTextDetalles = findViewById(R.id.EditTextDetalles)
        EditTextUbicación = findViewById(R.id.EditTextUbicación)
        ParqueBtn = findViewById(R.id.ParqueBtn)
        HotelBtn = findViewById(R.id.HotelBtn)
        RestauranteBtn = findViewById(R.id.RestauranteBtn)
        TiendaBtn = findViewById(R.id.TiendaBtn)
        ParqueBtnTxt = findViewById(R.id.ParqueBtnTxt)
        HotelBtnTxt = findViewById(R.id.HotelBtnTxt)
        RestauranteBtnTxt = findViewById(R.id.RestauranteBtnTxt)
        TiendaBtnTxt = findViewById(R.id.TiendaBtnTxt)
        UrlFotoSitio = findViewById(R.id.UrlFotoSitio)
        BotonSubirSitioFirebase = findViewById(R.id.BotonSubirSitioFirebase)

        reference = FirebaseDatabase.getInstance().getReference()

        ParqueBtn.setOnClickListener {
            SeleccionCategoria.setText("Parque")
        }

        ParqueBtnTxt.setOnClickListener {
            SeleccionCategoria.setText("Parque")
        }

        HotelBtn.setOnClickListener {
            SeleccionCategoria.setText("Hotel")
        }

        HotelBtnTxt.setOnClickListener {
            SeleccionCategoria.setText("Hotel")
        }

        RestauranteBtn.setOnClickListener {
            SeleccionCategoria.setText("Restaurante")
        }

        RestauranteBtnTxt.setOnClickListener {
            SeleccionCategoria.setText("Restaurante")
        }

        TiendaBtn.setOnClickListener {
            SeleccionCategoria.setText("Tienda")
        }

        TiendaBtnTxt.setOnClickListener {
            SeleccionCategoria.setText("Tienda")
        }

        Atras.setOnClickListener {
            val intent = Intent(this, VerSitiosTuristicos::class.java)
            startActivity(intent)
            finish()
        }

        Btn_Seleccionar.setOnClickListener {
            AbrirGaleria()
        }

        BotonSubirSitioFirebase.setOnClickListener {
            SubirInformacion()
        }

        Btn_tomar.setOnClickListener {
            TomarFoto()
        }
    }

    private fun AbrirGaleria(){
        ImagePicker.with(this).galleryOnly()
            .crop()
            .start(REQUEST_FROM_GALERY)
    }

    private fun TomarFoto(){
        ImagePicker.with(this).cameraOnly()
            .crop()
            .start(REQUEST_FROM_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                REQUEST_FROM_CAMERA -> {
                    Img_foto_turistica.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSeleccionImagen.visibility = View.GONE
                    AvisoSeleccionImagen.visibility = View.INVISIBLE
                }
                REQUEST_FROM_GALERY -> {
                    Img_foto_turistica.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSeleccionImagen.visibility = View.GONE
                    AvisoSeleccionImagen.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun uploadImage(mContext: Context, imageURI: Uri){

        //Let´s compress images before uploading into firebase
        var bytes = ByteArray(0)
        try{
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageURI)
            val stream = ByteArrayOutputStream()
            imageBitmap .compress(Bitmap.CompressFormat.JPEG, 15, stream);
            bytes = stream.toByteArray()
        } catch (e: IOException){
            e.printStackTrace()
        }

        val ImagenFileName = "ImagenesProductos ${System.currentTimeMillis()}"
        val uploadTask = mStorageRef.child(ImagenFileName).putBytes(bytes)

        uploadTask.addOnSuccessListener {
            Log.e(TAG, "Imagen cargada con éxito")
            val DescargarUrlImagen = mStorageRef.child(ImagenFileName).downloadUrl
            DescargarUrlImagen.addOnSuccessListener {
                UrlFotoSitio.setText("$it")
            }.addOnFailureListener{
                Toast.makeText(this, "No se pudo cargar la Url de la imagen", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Log.e(TAG, "Carga de imagen fallida ${it.printStackTrace()}")

        }
    }

    private fun SubirInformacion(){
        val Categoria = SeleccionCategoria.text.toString()
        val Nombre = EditTextNombre.text.toString()
        val Imagen = UrlFotoSitio.text.toString()
        val Detalles = EditTextDetalles.text.toString()
        val Ubicacion = EditTextUbicación.text.toString()
        val Id = reference.push().key!!

        if (!Nombre.isEmpty()){
            if (!Imagen.isEmpty()){
                if (!Detalles.isEmpty()){
                    if (!Ubicacion.isEmpty()){
                        val map: MutableMap<String, Any> = HashMap()
                        map["Id"] = Id
                        map["Nombre"] = Nombre
                        map["Imagen"] = Imagen
                        map["Detalles"] = Detalles
                        map["Ubicacion"] = Ubicacion
                        map["Categoria"] = Categoria
                        map["Puntuacion"] = ""

                        if (Categoria.equals("Parque")){
                            reference.child(Categoria).child(Id).setValue(map).addOnCompleteListener{
                                reference.child("SitiosRaquira").child(Id).setValue(map)
                                Toast.makeText(this, "Se ha creado un nuevo sitio turistico", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, VerSitiosTuristicos::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                            }
                        }else if (Categoria.equals("Hotel")){
                            reference.child(Categoria).child(Id).setValue(map).addOnCompleteListener{
                                reference.child("SitiosRaquira").child(Id).setValue(map)
                                Toast.makeText(this, "Se ha creado un nuevo sitio turistico", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, VerSitiosTuristicos::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                            }
                        }else if (Categoria.equals("Restaurante")){
                            reference.child(Categoria).child(Id).setValue(map).addOnCompleteListener{
                                reference.child("SitiosRaquira").child(Id).setValue(map)
                                Toast.makeText(this, "Se ha creado un nuevo sitio turistico", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, VerSitiosTuristicos::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                            }
                        }else if (Categoria.equals("Tienda")){
                            reference.child(Categoria).child(Id).setValue(map).addOnCompleteListener{
                                reference.child("SitiosRaquira").child(Id).setValue(map)
                                Toast.makeText(this, "Se ha creado un nuevo sitio turistico", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, VerSitiosTuristicos::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Debe ingresar la ubicación del sitio", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Debe ingresar la descripción del sitio", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Debe ingresar la imagen del sitio", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Debe ingresar el nombre del sitio", Toast.LENGTH_SHORT).show()
        }
    }
}