package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_movie.*

class CreateMovieActivity : AppCompatActivity() {

    lateinit var dbHandler: DBPeliculaHandlerAplicacion
    var idActor: Int = 0
    var pelicula: Pelicula? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_movie)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewPelicula.text = getString(R.string.edit_movie)
            pelicula = intent.getParcelableExtra("Pelicula")
            fillFields()
            tipo = true
        }

        idActor = intent.getIntExtra("idActor", 0)

        dbHandler = DBPeliculaHandlerAplicacion(this)

        btnCrearPelicula.setOnClickListener{
            v: View? ->  crearPelicula()
        }
    }

    fun fillFields() {
        txtIdPelicula.setText(pelicula?.idPelicula.toString())
        txtNombrePelicula.setText(pelicula?.nombre)
        txtAnioLanzamiento.setText(pelicula?.anioLanzamiento.toString())
        txtRating.setText(pelicula?.rating.toString())
        txtActoresPrincipales.setText(pelicula?.actoresPrincipales)
        txtSinopsis.setText(pelicula?.sinopsis)
    }

    fun crearPelicula() {
        var idPelicula = txtIdPelicula.text.toString().toInt()
        var nombre = txtNombrePelicula.text.toString()
        var anioLanzamiento = txtAnioLanzamiento.text.toString().toInt()
        var rating = txtRating.text.toString().toInt()
        var actoresPrincipales = txtActoresPrincipales.text.toString()
        var sinopsis = txtSinopsis.text.toString()
        var pelicula = Pelicula(idPelicula, nombre, anioLanzamiento, rating, actoresPrincipales, sinopsis, idActor)

        if (!tipo) {
            dbHandler.insertarPelicula(pelicula)
        } else {
            dbHandler.updatePelicula(pelicula)
        }

        finish()
    }
}
