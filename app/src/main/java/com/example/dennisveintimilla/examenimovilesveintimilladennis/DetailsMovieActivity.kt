package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dennisveintimilla.examenimovilesveintimilladennis.R
import kotlinx.android.synthetic.main.activity_details_book.*

class DetailsMovieActivity : AppCompatActivity() {

    var libro: Pelicula? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_book)

        libro = intent.getParcelableExtra("libro")

        txtShowIdPelicula.text = libro?.idPelicula.toString()
        txtShowNombrePelicula.text = libro?.nombre
        txtShowAnioLanzamiento.text = libro?.anioLanzamiento.toString()
        txtShowRating.text = libro?.rating.toString()
        txtShowActoresPrincipales.text = libro?.actoresPrincipales
        txtShowSinopsis.text = libro?.sinopsis
    }
}
