package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var actor: Actor? = null
    lateinit var adaptador: MovieAdapter
    lateinit var peliculas: ArrayList<Pelicula>
    lateinit var dbHandler: DBPeliculaHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        actor = intent.getParcelableExtra("actor")

        txtShowNombresActor.text = actor?.nombres
        txtShowApellidosActor.text = actor?.apellidos
        txtShowFechaNActor.text = actor?.fechaNacimiento
        txtShowNumPeliculas.text = actor?.numeroPeliculas.toString()
        txtShowRetirado.text = if(actor?.retirado == 1) getString(R.string.yes) else getString(R.string.no)

        dbHandler = DBPeliculaHandlerAplicacion(this)
        peliculas = dbHandler.getPeliculasList(actor?.id!!)

        val layoutManager = LinearLayoutManager(this)
        adaptador = MovieAdapter(peliculas)
        recycler_view_book.layoutManager = layoutManager
        recycler_view_book.itemAnimator = DefaultItemAnimator()
        recycler_view_book.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_book)

        btnNuevaPelicula.setOnClickListener{
            v: View? ->  crearPelicula()
        }
    }

    fun crearPelicula() {
        val intent = Intent(this, CreateMovieActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("idActor", actor?.id!!)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var pelicula = peliculas[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.pelicula)} - ${getString(R.string.app_name)}")
                intent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.idPelicula)} ${pelicula.idPelicula}\n${getString(R.string.name)} ${pelicula.nombre}\n${getString(R.string.rating)} ${pelicula.rating}\n${getString(R.string.sinopsis)} ${pelicula.sinopsis}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateMovieActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("pelicula", pelicula)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, { dialog, which ->
                            dbHandler.deletePelicula(pelicula.idPelicula)
                            finish()
                            startActivity(intent)
                        }
                        )
                        .setNegativeButton(R.string.no, null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
