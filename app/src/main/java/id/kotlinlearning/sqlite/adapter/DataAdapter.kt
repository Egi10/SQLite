package id.kotlinlearning.sqlite.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.models.Data
import kotlinx.android.synthetic.main.costum_layout_activity_main.view.*

class DataAdapter(private val dataList : ArrayList<Data>, private val listener: (Data) -> Unit) : RecyclerView.Adapter<DataAdapter.Holder>() {
    lateinit var listenerRemove: (Data, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.costum_layout_activity_main, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(dataList[position], position, listener)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: Data, position: Int, listener: (Data) -> Unit) {
            with(itemView) {
                tvNamaLengkap.text = data.namaLengkap
                tvUmur.text = String.format("Umur : %s", data.umur)
                tvStatus.text = String.format("Status : %s", data.status)

                ivTrash.setOnClickListener {
                    listenerRemove.invoke(data, position)
                }

                cardView.setOnClickListener {
                    listener(data)
                }
            }
        }
    }
}