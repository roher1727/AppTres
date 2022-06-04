package com.clase2503.apptres.model

import com.google.gson.annotations.SerializedName

class ProductoDetalles {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("imag_url")
    var imagUrl: String? = null

    @SerializedName("desc")
    var desc: String? = null

    override fun toString(): String {
        return name+imagUrl+desc
    }

}


