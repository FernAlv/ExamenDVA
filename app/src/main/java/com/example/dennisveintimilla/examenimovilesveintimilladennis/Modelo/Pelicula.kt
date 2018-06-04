package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.os.Parcel
import android.os.Parcelable

class Pelicula(var idPelicula: Int, var nombre: String, var anioLanzamiento: Int, var rating: Int, var actoresPrincipales: String, var sinopsis: String, var actorID: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(idPelicula)
        destino?.writeString(nombre)
        destino?.writeInt(anioLanzamiento)
        destino?.writeInt(rating)
        destino?.writeString(actoresPrincipales)
        destino?.writeString(sinopsis)
        destino?.writeInt(actorID)
    }

    companion object CREATOR : Parcelable.Creator<Pelicula> {
        override fun createFromParcel(parcel: Parcel): Pelicula {
            return Pelicula(parcel)
        }

        override fun newArray(size: Int): Array<Pelicula?> {
            return arrayOfNulls(size)
        }
    }

}