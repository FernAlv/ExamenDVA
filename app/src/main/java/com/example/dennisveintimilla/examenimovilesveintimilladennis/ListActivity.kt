package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.example.dennisveintimilla.examenimovilesveintimilladennis.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var adaptador: ActorAdapter
    lateinit var actores: ArrayList<Actor>
    lateinit var dbHandler: DBActorHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dbHandler = DBActorHandlerAplicacion(this)
        actores = dbHandler.getActorsList()

        val layoutManager = LinearLayoutManager(this)
        adaptador = ActorAdapter(actores)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var actor = actores[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.actor)} - ${getString(R.string.app_name)}")
                intent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.name)} ${actor.nombres} ${actor.apellidos}\n${getString(R.string.numero_peliculas)} ${actor.numeroPeliculas}\n${getString(R.string.fecha_nacimiento)} ${actor.fechaNacimiento}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("actor", actor)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmation)
                        .setPositiveButton(R.string.yes, { dialog, which ->
                            dbHandler.deleteActor(actor.id)
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
