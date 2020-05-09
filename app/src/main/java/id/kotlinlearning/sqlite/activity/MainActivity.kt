package id.kotlinlearning.sqlite.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.adapter.DataAdapter
import id.kotlinlearning.sqlite.models.Data
import id.kotlinlearning.sqlite.sqlite.Helper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.costum_layout_empty.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DataAdapter
    private var data = ArrayList<Data>()
    private lateinit var helper: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // OnClick In Fab
        fab.setOnClickListener {
            val intent = Intent(baseContext, CreateActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Deklarasi Helper
        helper = Helper(this)

        swipeRefresh.post {
            loadData()
        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true

            loadData()
        }
    }

    private fun loadData() {
        swipeRefresh.isRefreshing = false

        // Show Data in SQLite
        data = helper.readData()

        empty()

        adapter = DataAdapter(data) {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }
        adapter.listenerRemove = { data, position ->
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Peringatan")
            alertDialog.setMessage("Apakah Yakin Menghapus Datanya Jomblo ?")
            alertDialog.setPositiveButton("Yakin") { dialog, _ ->
                val id = data.dataId

                // Remove Data Id in SQLite
                helper.deleteData(id)

                this.data.removeAt(position)
                empty()
                adapter.notifyDataSetChanged()

                Toast.makeText(this, "Data Sudah Terhapus", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }
            val dialog = alertDialog.create()
            dialog.show()
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun empty() {
        if (data.isEmpty()) {
            empty.visibility = View.VISIBLE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Peringatan")
            alertDialog.setMessage("Apakah Yakin Keluar Ni Jomblo ?")
            alertDialog.setPositiveButton("Yakin") { _, _ ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            alertDialog.setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }
            val dialog = alertDialog.create()
            dialog.show()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
