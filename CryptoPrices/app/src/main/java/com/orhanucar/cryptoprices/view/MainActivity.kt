package com.orhanucar.cryptoprices.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanucar.cryptoprices.R
import com.orhanucar.cryptoprices.adapter.RecyclerViewAdapter
import com.orhanucar.cryptoprices.model.CrptoModel
import com.orhanucar.cryptoprices.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CrptoModel>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        compositeDisposable = CompositeDisposable()

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadData()

    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI :: class.java)


        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this :: handleResponse))


        /*
        val service = retrofit.create(CryptoAPI :: class.java)
        val call = service.getData()
        call.enqueue(object: Callback<List<CrptoModel>> {
            override fun onResponse(
                call: Call<List<CrptoModel>>,
                response: Response<List<CrptoModel>>
            ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            cryptoModels = ArrayList(it)
                            cryptoModels?.let {
                                recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
                                recyclerView.adapter = recyclerViewAdapter
                            }
                        }
                    }
            }

            override fun onFailure(call: Call<List<CrptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

         */

    }

    private fun handleResponse(cryptoList: List<CrptoModel>) {
        cryptoModels = ArrayList(cryptoList)
        cryptoModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(crptoModel: CrptoModel) {
        Toast.makeText(this,"Clicked: ${crptoModel.currency}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

}