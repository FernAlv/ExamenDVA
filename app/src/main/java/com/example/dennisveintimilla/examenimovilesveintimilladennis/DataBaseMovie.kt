package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBasePelicula {
    companion object {
        val DB_NAME = "Peliculas"
        val TABLE_NAME = "pelicula"
        val CAMPO_ID = "id"
        val CAMPO_NOMBRE = "nombre"
        val CAMPO_ANIOLANZAMIENTO = "anioLanzamiento"
        val CAMPO_RATING = "rating"
        val CAMPO_ACTORESPRINCIPALES = "actoresPrincipales"
        val CAMPO_SINOPSIS = "sinopsis"
        val CAMPO_ACTORID = "actorID"
    }
}

class DBPeliculaHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBasePelicula.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBasePelicula.TABLE_NAME} (${DataBasePelicula.CAMPO_ID} INTEGER PRIMARY KEY, ${DataBasePelicula.CAMPO_NOMBRE} VARCHAR(50),${DataBasePelicula.CAMPO_ANIOLANZAMIENTO} INTEGER,${DataBasePelicula.CAMPO_RATING} INTEGER, ${DataBasePelicula.CAMPO_ACTORESPRINCIPALES} VARCHAR(20), ${DataBasePelicula.CAMPO_SINOPSIS} VARCHAR(20),  ${DataBasePelicula.CAMPO_ACTORID} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarPelicula(pelicula: Pelicula) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBasePelicula.CAMPO_ID, pelicula.idPelicula)
        cv.put(DataBasePelicula.CAMPO_NOMBRE, pelicula.nombre)
        cv.put(DataBasePelicula.CAMPO_ANIOLANZAMIENTO, pelicula.anioLanzamiento)
        cv.put(DataBasePelicula.CAMPO_RATING, pelicula.rating)
        cv.put(DataBasePelicula.CAMPO_ACTORESPRINCIPALES, pelicula.actoresPrincipales)
        cv.put(DataBasePelicula.CAMPO_SINOPSIS, pelicula.sinopsis)
        cv.put(DataBasePelicula.CAMPO_ACTORID, pelicula.actorID)

        val resultado = dbWriteable.insert(DataBasePelicula.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updatePelicula(pelicula: Pelicula) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBasePelicula.CAMPO_ID, pelicula.idPelicula)
        cv.put(DataBasePelicula.CAMPO_NOMBRE, pelicula.nombre)
        cv.put(DataBasePelicula.CAMPO_ANIOLANZAMIENTO, pelicula.anioLanzamiento)
        cv.put(DataBasePelicula.CAMPO_RATING, pelicula.rating)
        cv.put(DataBasePelicula.CAMPO_ACTORESPRINCIPALES, pelicula.actoresPrincipales)
        cv.put(DataBasePelicula.CAMPO_SINOPSIS, pelicula.sinopsis)
        cv.put(DataBasePelicula.CAMPO_ACTORID, pelicula.actorID)

        val whereClause = "${DataBasePelicula.CAMPO_ID} = ${pelicula.idPelicula}"
        val resultado = dbWriteable.update(DataBasePelicula.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deletePelicula(idPelicula: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBasePelicula.CAMPO_ID} = $idPelicula"
        return dbWriteable.delete(DataBasePelicula.TABLE_NAME, whereClause, null) > 0
    }

    fun getPeliculasList(idActor: Int): ArrayList<Pelicula> {
        var lista = ArrayList<Pelicula>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBasePelicula.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val idPelicula = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val anioLanzamiento = resultado.getString(2).toInt()
                val rating = resultado.getString(3).toInt()
                val actoresPrincipales = resultado.getString(4)
                val sinopsis = resultado.getString(5)
                val actorID = resultado.getString(6).toInt()

                lista.add(Pelicula(idPelicula, nombre, anioLanzamiento, rating, actoresPrincipales, sinopsis, actorID))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
