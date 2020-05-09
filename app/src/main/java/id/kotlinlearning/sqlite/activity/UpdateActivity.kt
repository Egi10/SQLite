package id.kotlinlearning.sqlite.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        val data = intent.getParcelableExtra<Data>("data")
        data?.let {
            etNamaLengkap.setText(it.namaLengkap)
            etUmur.setText(it.umur)
            etStatus.setText(it.status)
        }

        btnUpdate.setOnClickListener {
            val namaLengkap = etNamaLengkap.text.toString()
            val umur = etUmur.text.toString()
            val status = etStatus.text.toString()

            helper.updateData(Data(data.dataId, namaLengkap, umur, status))

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
