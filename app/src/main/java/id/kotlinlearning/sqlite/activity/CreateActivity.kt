package id.kotlinlearning.sqlite.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent
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
            // Get EditText
            val namaLengkap = etNamaLengkap.text.toString()
            val umur = etUmur.text.toString()
            val status = etStatus.text.toString()

            // Save Data
            helper.insertData(namaLengkap,umur, status)

            // Set EditText To Empty
            etNamaLengkap.setText("")
            etUmur.setText("")
            etStatus.setText("")

            // Show Snackbar
            Snackbar.make(view, "Your Data Already Saved", Snackbar.LENGTH_LONG)
                    .setAction("Ok") {
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.show()
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
