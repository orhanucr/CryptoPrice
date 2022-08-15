package com.orhanucar.cryptoprices.model

import com.google.gson.annotations.SerializedName

data class CrptoModel (
    //@SerializedName("currency")
    val currency: String,

    //@SerializedName("price")
    val price: String
    )
