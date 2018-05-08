package id.kotlinlearning.sqlite.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import id.kotlinlearning.sqlite.R
import id.kotlinlearning.sqlite.sqlite.Helper
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {
    lateinit var helper : Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        helper = Helper(this)

        btnSimpan.setOnClickListener {view ->
            var namaLengkap = etNamaLengkap.text.toString()
            var umur = etUmur.text.toString()
            var status = etStatus.text.toString()

            var result = helper.insertData(namaLengkap,umur, status)

            Log.d("Hasil ", result.toString())

            etNamaLengkap.setText("")
            etUmur.setText("")
            etStatus.setText("")

            Snackbar.make(view, "Your Data Already Saved", Snackbar.LENGTH_LONG)
                    .setAction("Ok", View.OnClickListener {
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }).show()
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