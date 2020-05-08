package id.kotlinlearning.sqlite.sqlite

import android.provider.BaseColumns

object Contract {
    class Biodata : BaseColumns {
        companion object {
            const val TABLE_NAME = "biodata"
            const val COLUMNS_USER_ID = "_id"
            const val COLUMNS_NAMA_LENGKAP = "namaLengkap"
            const val COLUMNS_UMUR = "umur"
            const val COLUMNS_STATUS = "status"
        }
    }
}