package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseActor {
    companion object {
        val DB_NAME = "autorLibro"
        val TABLE_NAME = "autors"
        val CAMPO_ID = "id"
        val CAMPO_NOMBRE = "nombres"
        val CAMPO_APELLIDO = "apellidos"
        val CAMPO_FECHANACIMIENTO = "fechaNacimiento"
        val CAMPO_NUMEROLIBROS = "numeroLibros"
        val CAMPO_ECUATORIANO = "ecuatoriano"
    }
}

class DBActorHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseActor.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseActor.TABLE_NAME} (${DataBaseActor.CAMPO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DataBaseActor.CAMPO_NOMBRE} VARCHAR(50),${DataBaseActor.CAMPO_APELLIDO} VARCHAR(50),${DataBaseActor.CAMPO_FECHANACIMIENTO} VARCHAR(20), ${DataBaseActor.CAMPO_NUMEROLIBROS} INTEGER, ${DataBaseActor.CAMPO_ECUATORIANO} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarAutor(autor: Actor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseActor.CAMPO_NOMBRE, autor.nombres)
        cv.put(DataBaseActor.CAMPO_APELLIDO, autor.apellidos)
        cv.put(DataBaseActor.CAMPO_FECHANACIMIENTO, autor.fechaNacimiento)
        cv.put(DataBaseActor.CAMPO_NUMEROLIBROS, autor.numeroPeliculas)
        cv.put(DataBaseActor.CAMPO_ECUATORIANO, autor.retirado)

        val resultado = dbWriteable.insert(DataBaseActor.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateAutor(actor: Actor) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseActor.CAMPO_NOMBRE, actor.nombres)
        cv.put(DataBaseActor.CAMPO_APELLIDO, actor.apellidos)
        cv.put(DataBaseActor.CAMPO_FECHANACIMIENTO, actor.fechaNacimiento)
        cv.put(DataBaseActor.CAMPO_NUMEROLIBROS, actor.numeroPeliculas)
        cv.put(DataBaseActor.CAMPO_ECUATORIANO, actor.retirado)

        val whereClause = "${DataBaseActor.CAMPO_ID} = ${actor.id}"
        val resultado = dbWriteable.update(DataBaseActor.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteActor(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseActor.CAMPO_ID} = $id"
        return dbWriteable.delete(DataBaseActor.TABLE_NAME, whereClause, null) > 0
    }

    fun getActorsList(): ArrayList<Actor> {
        var lista = ArrayList<Actor>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseActor.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val apellido = resultado.getString(2)
                val fechaNacimiento = resultado.getString(3)
                val numeroLibros = resultado.getString(4).toInt()
                val ecuatoriano = resultado.getString(5).toInt()

                lista.add(Actor(id, nombre, apellido, fechaNacimiento, numeroLibros, ecuatoriano))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
