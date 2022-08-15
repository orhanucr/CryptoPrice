package com.orhanucar.cryptoprices.service

import com.orhanucar.cryptoprices.model.CrptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET


interface CryptoAPI {

    //GET, POST, UPDATE, DELETE
    //https://raw.githubusercontent.com/
    //atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //fun getData(): Call<List<CrptoModel>>

    fun getData(): Observable<List<CrptoModel>>
}