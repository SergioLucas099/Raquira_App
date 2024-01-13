package com.proyecto.raquiraturistica

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mannan.translateapi.Language
import com.mannan.translateapi.TranslateAPI
import com.mannan.translateapi.TranslateAPI.TranslateListener
import com.proyecto.raquiraturistica.Adapter.ComentariosAdapter
import com.proyecto.raquiraturistica.Model.ComentariosModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import java.lang.Math.ceil
import java.lang.Math.round
import java.text.DecimalFormat
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class DetallesSitio : AppCompatActivity() {

    private lateinit var BotonAtrasEditarSitio: ImageView
    private lateinit var DetallesNombreSitio: TextView
    private lateinit var DetallesImagen: ImageView
    private lateinit var DetallesTxt: TextView
    private lateinit var UbicacionTxt: TextView
    private lateinit var PuntuacionTxt: TextView
    private lateinit var CalificarLugarTxt: TextView
    private lateinit var DetallesSitioTuristico: TextView
    private lateinit var UbicacionSitioTuristico: ImageView
    private lateinit var idioma_español: ImageView
    private lateinit var idioma_ingles: ImageView
    private lateinit var R_Star: RatingBar
    private lateinit var Puntuacion: TextView
    private lateinit var EditTextAutor: EditText
    private lateinit var TextFieldAutor: TextInputLayout
    private lateinit var TextFieldComentario: TextInputLayout
    private lateinit var EditTextComentario: EditText
    private lateinit var puntuaciontotal: TextView
    private lateinit var Comentarios: TextView
    private lateinit var BotonCalificar: Button
    private lateinit var reference: DatabaseReference
    private lateinit var Rv_Comentarios: RecyclerView
    private lateinit var lista : ArrayList<ComentariosModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_sitio)

        BotonAtrasEditarSitio = findViewById(R.id.BotonAtrasEditarSitio)
        DetallesNombreSitio = findViewById(R.id.DetallesNombreSitio)
        DetallesImagen = findViewById(R.id.DetallesImagen)
        DetallesTxt = findViewById(R.id.DetallesTxt)
        UbicacionTxt = findViewById(R.id.UbicacionTxt)
        PuntuacionTxt = findViewById(R.id.PuntuacionTxt)
        CalificarLugarTxt = findViewById(R.id.CalificarLugarTxt)
        DetallesSitioTuristico = findViewById(R.id.DetallesSitioTuristico)
        UbicacionSitioTuristico = findViewById(R.id.UbicacionSitioTuristico)
        idioma_español = findViewById(R.id.idioma_español)
        idioma_ingles = findViewById(R.id.idioma_ingles)
        R_Star = findViewById(R.id.R_Star)
        Puntuacion = findViewById(R.id.Puntuacion)
        EditTextAutor = findViewById(R.id.EditTextAutor)
        TextFieldAutor = findViewById(R.id.TextFieldAutor)
        TextFieldComentario = findViewById(R.id.TextFieldComentario)
        EditTextComentario = findViewById(R.id.EditTextComentario)
        puntuaciontotal = findViewById(R.id.puntuaciontotal)
        BotonCalificar = findViewById(R.id.BotonCalificar)
        Comentarios = findViewById(R.id.ComentariosTxt)
        Rv_Comentarios = findViewById(R.id.Rv_Comentarios)
        reference = FirebaseDatabase.getInstance().getReference()

        var Nombre: String? = intent.getStringExtra("Nombre")
        var Imagen: String? = intent.getStringExtra("Imagen")
        var Detalles: String? = intent.getStringExtra("Detalles")
        var Ubicacion: String? = intent.getStringExtra("Ubicacion")
        var Categoria: String? = intent.getStringExtra("Categoria")

        DetallesNombreSitio.setText(Nombre)
        Picasso.get().load(Imagen).into(DetallesImagen)
        DetallesSitioTuristico.setText(Detalles)

        R_Star.rating = 2.5f
        R_Star.stepSize = .5f

        Rv_Comentarios.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        lista = arrayListOf<ComentariosModel>()
        Rv_Comentarios.visibility = View.GONE
        FirebaseDatabase.getInstance().reference.child("Comentarios").child(Nombre.toString())
            .addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(ComentariosModel::class.java)
                            lista.add(data!!)
                        }
                        val Adapter = ComentariosAdapter(lista)
                        Rv_Comentarios.adapter = Adapter
                        Rv_Comentarios.visibility = View.VISIBLE
                    }
                    CalcularPromedio(lista).toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        R_Star.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            Puntuacion.setText(rating.toString())
        }

        idioma_ingles.setOnClickListener {
            val translateAPI = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                DetallesSitioTuristico.getText().toString(),
            )

            translateAPI.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesSitioTuristico.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI1 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                DetallesTxt.getText().toString(),
            )

            translateAPI1.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI2 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                DetallesNombreSitio.getText().toString(),
            )

            translateAPI2.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesNombreSitio.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI3 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                UbicacionTxt.getText().toString(),
            )

            translateAPI3.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    UbicacionTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI4 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                PuntuacionTxt.getText().toString(),
            )

            translateAPI4.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    PuntuacionTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI5 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                CalificarLugarTxt.getText().toString(),
            )

            translateAPI5.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    CalificarLugarTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI6 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                TextFieldAutor.hint.toString(),
            )

            translateAPI6.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    TextFieldAutor.setHint(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI7 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                TextFieldComentario.hint.toString(),
            )

            translateAPI7.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    TextFieldComentario.setHint(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI8 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                BotonCalificar.text.toString(),
            )

            translateAPI8.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    BotonCalificar.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI9 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.ENGLISH,
                Comentarios.text.toString(),
            )

            translateAPI9.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    Comentarios.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            idioma_ingles.visibility = View.INVISIBLE
            idioma_español.visibility = View.VISIBLE
        }

        idioma_español.setOnClickListener {
            val translateAPI = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                DetallesSitioTuristico.getText().toString()
            )

            translateAPI.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesSitioTuristico.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI1 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                DetallesTxt.getText().toString(),
            )

            translateAPI1.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI2 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                DetallesNombreSitio.getText().toString(),
            )

            translateAPI2.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    DetallesNombreSitio.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI3 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                UbicacionTxt.getText().toString(),
            )

            translateAPI3.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    UbicacionTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI4 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                PuntuacionTxt.getText().toString(),
            )

            translateAPI4.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    PuntuacionTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI5 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                CalificarLugarTxt.getText().toString(),
            )

            translateAPI5.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    CalificarLugarTxt.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI6 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                TextFieldAutor.hint.toString(),
            )

            translateAPI6.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    TextFieldAutor.setHint(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI7 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                TextFieldComentario.hint.toString(),
            )

            translateAPI7.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    TextFieldComentario.setHint(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI8 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                BotonCalificar.text.toString(),
            )

            translateAPI8.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    BotonCalificar.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            val translateAPI9 = TranslateAPI(
                Language.AUTO_DETECT,
                Language.SPANISH,
                Comentarios.text.toString(),
            )

            translateAPI9.setTranslateListener(object : TranslateListener {
                override fun onSuccess(translatedText: String) {
                    Comentarios.setText(translatedText)
                }

                override fun onFailure(ErrorText: String) {
                    Log.d("Error", ErrorText)
                }
            })

            idioma_español.visibility = View.INVISIBLE
            idioma_ingles.visibility = View.VISIBLE
        }

        BotonAtrasEditarSitio.setOnClickListener {
            val intent = Intent(this@DetallesSitio, SitiosInteres::class.java)
            intent.putExtra("Dato", Categoria)
            startActivity(intent)
            finish()
        }

        UbicacionSitioTuristico.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(Ubicacion))
            startActivity(i)
        }

        BotonCalificar.setOnClickListener {

            val Id = reference.push().key!!
            val map: MutableMap<String, Any> = HashMap()
            map["Puntuacion"] = Puntuacion.text.toString()
            map["Autor"] = EditTextAutor.text.toString()
            map["Comentario"] = EditTextComentario.text.toString()

            if(!Puntuacion.text.toString().equals("0.0")){
                if(EditTextAutor.text.toString().isEmpty() && EditTextComentario.text.toString().isEmpty()){
                    map["Autor"] = "Anonimo"
                    map["Comentario"] = "Sin Comentario"
                    reference.child("Comentarios").child(Nombre.toString()).child(Id)
                        .setValue(map).addOnCompleteListener {
                            Toast.makeText(this, "Se ha calificado este lugar", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "Gracias por su tiempo", Toast.LENGTH_SHORT).show()
                            Puntuacion.setText("0.0")
                            EditTextAutor.setText("")
                            EditTextComentario.setText("")
                        }.addOnFailureListener {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                }else{
                    reference.child("Comentarios").child(Nombre.toString()).child(Id)
                        .setValue(map).addOnCompleteListener {
                            Toast.makeText(this, "Se ha calificado este lugar \n Gracias por su tiempo", Toast.LENGTH_SHORT).show()
                            Puntuacion.setText("0.0")
                            EditTextAutor.setText("")
                            EditTextComentario.setText("")
                        }.addOnFailureListener {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                }
            }else{
                Toast.makeText(this, "Nada para calificar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun CalcularPromedio(comentarios: List<ComentariosModel>){
        var IdCategoria: String? = intent.getStringExtra("Id")
        var Categoria: String? = intent.getStringExtra("Categoria")

        var ValorTotal = 0.0
        var TotalComentarios = 0

        for (micomentario in comentarios) {
            ValorTotal += micomentario.Puntuacion.toString().toDouble()
            TotalComentarios++
        }
        var Total = DecimalFormat("#.#").format(ValorTotal/TotalComentarios)
        puntuaciontotal.setText("${Total}")
        val map1: MutableMap<String, Any> = HashMap()
        map1["Puntuacion"] = puntuaciontotal.text
        reference.child(Categoria.toString()).child(IdCategoria.toString()).updateChildren(map1)
        reference.child("SitiosRaquira").child(IdCategoria.toString()).updateChildren(map1)
    }
}