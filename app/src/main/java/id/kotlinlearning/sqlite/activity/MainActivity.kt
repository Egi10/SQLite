package id.kotlinlearning.sqlite.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.adapter.DataAdapter
import id.kotlinlearning.sqlite.models.Data
import id.kotlinlearning.sqlite.sqlite.Helper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView : RecyclerView? = null
    private var empty : LinearLayout? = null
    private var adapter : DataAdapter? = null
    private var data = ArrayList<Data>()

    lateinit var helper : Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(baseContext, CreateActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        empty = findViewById(R.id.empty)

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

        data = helper.readData()

        if (data.isEmpty()){
            empty!!.visibility = View.VISIBLE
        }

        adapter = DataAdapter(data)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
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
