package id.kotlinlearning.sqlite.sqlite

import android.provider.BaseColumns

object Contract {
    class Biodata : BaseColumns {
        companion object {
            val TABLE_NAME = "biodata"
            val COLUMNS_USER_ID = "_id"
            val COLUMNS_NAMA_LENGKAP = "namaLengkap"
            val COLUMNS_UMUR = "umur"
            val COLUMNS_STATUS = "status"
        }
    }
}