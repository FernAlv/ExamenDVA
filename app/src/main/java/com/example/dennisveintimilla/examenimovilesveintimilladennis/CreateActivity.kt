package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    lateinit var dbHandler: DBActorHandlerAplicacion
    var actor: Actor? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewActor.text = getString(R.string.edit_actor)
            actor = intent.getParcelableExtra("actor")
            fillFields()
            tipo = true
        }

        dbHandler = DBActorHandlerAplicacion(this)

        btnCrearActor.setOnClickListener{
            v: View? -> crearActor()
        }
    }

    fun fillFields() {
        txtNombresActor.setText(actor?.nombres)
        txtApellidosActor.setText(actor?.apellidos)
        txtFechaActor.setText(actor?.fechaNacimiento)
        txtNumeroPeliculasActor.setText(actor?.numeroPeliculas.toString())
        if (actor?.retirado == 1) {
            switchRetirado.toggle()
        }
    }

    fun crearActor() {
        var nombres = txtNombresActor.text.toString()
        var apellidos = txtApellidosActor.text.toString()
        var fecha = txtFechaActor.text.toString()
        var numeroPeliculas = txtNumeroPeliculasActor.text.toString().toInt()
        var retirado = if (switchRetirado.isChecked) 1 else 0

        if (!tipo) {
            var actor = Actor(0, nombres, apellidos, fecha, numeroPeliculas, retirado)
            dbHandler.insertarAutor(actor)
        } else {
            var actor = Actor(actor?.id!!, nombres, apellidos, fecha, numeroPeliculas, retirado)
            dbHandler.updateAutor(actor)
        }

        irAListView()
    }

    fun irAListView() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}
