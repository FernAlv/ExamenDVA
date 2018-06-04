package com.example.dennisveintimilla.examenimovilesveintimilladennis

import android.os.Parcel
import android.os.Parcelable

class Actor(var id: Int, var nombres: String, var apellidos: String, var fechaNacimiento: String, var numeroPeliculas: Int, var retirado: Int): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(nombres)
        destino?.writeString(apellidos)
        destino?.writeString(fechaNacimiento)
        destino?.writeInt(numeroPeliculas)
        destino?.writeInt(retirado)
    }

    companion object CREATOR : Parcelable.Creator<Actor> {
        override fun createFromParcel(parcel: Parcel): Actor {
            return Actor(parcel)
        }

        override fun newArray(size: Int): Array<Actor?> {
            return arrayOfNulls(size)
        }
    }

}