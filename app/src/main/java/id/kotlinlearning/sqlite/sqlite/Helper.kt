package id.kotlinlearning.sqlite.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import id.kotlinlearning.sqlite.models.Data
import kotlin.collections.ArrayList

class Helper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //!! = Untuk Null Check

        db!!.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DROP_TABLE)
    }

    fun insertData(namaLengkap:String, umur:String, status:String) : Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(Contract.Biodata.COLUMNS_NAMA_LENGKAP, namaLengkap)
        values.put(Contract.Biodata.COLUMNS_UMUR, umur)
        values.put(Contract.Biodata.COLUMNS_STATUS, status)

        db.insert(Contract.Biodata.TABLE_NAME, null, values)

        return true
    }

    fun readData() : ArrayList<Data> {
        val data = ArrayList<Data>()

        val db = writableDatabase

        var cursor : Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM "+Contract.Biodata.TABLE_NAME, null)
        }catch (e: SQLiteException){
            db.execSQL(SQL_CREATE_TABLE)
            return ArrayList()
        }

        var dataId : Int
        var namaLengkap : String
        var umur : String
        var status : String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dataId = cursor.getInt(cursor.getColumnIndex(Contract.Biodata.COLUMNS_USER_ID))
                namaLengkap = cursor.getString(cursor.getColumnIndex(Contract.Biodata.COLUMNS_NAMA_LENGKAP))
                umur = cursor.getString(cursor.getColumnIndex(Contract.Biodata.COLUMNS_UMUR))
                status = cursor.getString(cursor.getColumnIndex(Contract.Biodata.COLUMNS_STATUS))

                data.add(Data(dataId, namaLengkap, umur, status))
                cursor.moveToNext()
            }
        }
        return data
    }

    fun deleteData(idData : Int) : Boolean{
        val db = writableDatabase

        val id = Contract.Biodata.COLUMNS_USER_ID + " = " + idData

        db.delete(Contract.Biodata.TABLE_NAME, id, null)

        return true
    }

    fun updateData(data : Data) {
        val db = writableDatabase

        var values = ContentValues()
        values.put(Contract.Biodata.COLUMNS_USER_ID, data.dataId)
        values.put(Contract.Biodata.COLUMNS_NAMA_LENGKAP, data.namaLengkap)
        values.put(Contract.Biodata.COLUMNS_UMUR, data.umur)
        values.put(Contract.Biodata.COLUMNS_STATUS, data.status)

        val id = Contract.Biodata.COLUMNS_USER_ID + " = " + data.dataId

        db.update(Contract.Biodata.TABLE_NAME, values, id, null)
    }

    companion object {
        private val DATABASE_NAME = "data.db"
        private val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE = "CREATE TABLE " + Contract.Biodata.TABLE_NAME + " (" +
                Contract.Biodata.COLUMNS_USER_ID + " integer primary key autoincrement, " +
                Contract.Biodata.COLUMNS_NAMA_LENGKAP + " text not null, " +
                Contract.Biodata.COLUMNS_UMUR + " text not null, " +
                Contract.Biodata.COLUMNS_STATUS + " text not null)"

        private val SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Contract.Biodata.TABLE_NAME
    }
}