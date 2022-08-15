package com.orhanucar.cryptoprices.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanucar.cryptoprices.R
import com.orhanucar.cryptoprices.model.CrptoModel
import kotlinx.android.synthetic.main.row_layout.view.*
import okhttp3.internal.http2.Http2Connection

class RecyclerViewAdapter(private val cryptoList: ArrayList<CrptoModel>, private val listener: Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(crptoModel: CrptoModel)
    }

    private val colors: Array<String> = arrayOf("#48C9B0","#45B39D","#7DCEA0","#82E0AA","#F8C471","#F8C471","#F0B27A","#E59866","#D7DBDD","#D5DBDB","#99A3A4","#5D6D7E","#566573")

    class RowHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(crptoModel: CrptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(crptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 13]))
            itemView.text_name.text = crptoModel.currency
            itemView.text_price.text= crptoModel.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors, position, listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}