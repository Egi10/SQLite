package id.kotlinlearning.sqlite.adapter

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.activity.UpdateActivity
import id.kotlinlearning.sqlite.models.Data
import id.kotlinlearning.sqlite.sqlite.Helper

class DataAdapter(private val dataList : ArrayList<Data>) : RecyclerView.Adapter<DataAdapter.Holder>() {
    lateinit var helper : Helper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.costum_layout_activity_main, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = dataList[position]
        holder.namaLengkap.text = data.namaLengkap
        holder.umur.text = String.format("Umur : %s", data.umur)
        holder.status.text = String.format("Status : %s", data.status)

        holder.trash.setOnClickListener {view ->
            val alertDialog = AlertDialog.Builder(view.context)
            alertDialog.setTitle("Peringatan")
            alertDialog.setMessage("Apakah Yakin Menghapus Datanya Jomblo ?")
            alertDialog.setPositiveButton("Yakin") { dialog, _ ->
                val id = data.dataId

                helper = Helper(view.context)

                helper.deleteData(id)

                dataList.removeAt(position)
                this.notifyDataSetChanged()

                Toast.makeText(view.context, "Data Sudah Terhapus", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }
            val dialog = alertDialog.create()
            dialog.show()
        }

        holder.card.setOnClickListener { view ->
            val intent = Intent(view.context, UpdateActivity::class.java)
            intent.putExtra("data", data)
            view.context.startActivity(intent)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
        var namaLengkap : AppCompatTextView = view.findViewById(R.id.tvNamaLengkap)
        var umur : AppCompatTextView = view.findViewById(R.id.tvUmur)
        var status : AppCompatTextView = view.findViewById(R.id.tvStatus)
        var trash : AppCompatImageView = view.findViewById(R.id.ivTrash)
        var card : CardView = view.findViewById(R.id.cardView)
    }
}