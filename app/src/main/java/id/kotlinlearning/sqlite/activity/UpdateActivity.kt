package id.kotlinlearning.sqlite.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.models.Data
import id.kotlinlearning.sqlite.sqlite.Helper
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    lateinit var helper : Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        helper = Helper(this)

        val intent = getIntent()
        val Id = intent.getIntExtra("Id",0)
        val NamaLengkap = intent.getStringExtra("NamaLengkap")
        val Umur = intent.getStringExtra("Umur")
        val Status = intent.getStringExtra("Status")

        if (intent != null) {
            etNamaLengkap.setText(NamaLengkap)
            etUmur.setText(Umur)
            etStatus.setText(Status)
        } else {
            Log.d("Bermasalah ", "Data Null")
        }

        btnUpdate.setOnClickListener {
            val NamaLengkap = etNamaLengkap.text.toString()
            val Umur = etUmur.text.toString()
            val Status = etStatus.text.toString()

            helper.updateData(Data(Id, NamaLengkap, Umur, Status))

            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
