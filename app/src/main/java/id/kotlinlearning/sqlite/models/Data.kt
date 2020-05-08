package id.kotlinlearning.sqlite.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
        var dataId: Int,
        var namaLengkap: String,
        var umur: String,
        var status: String
) : Parcelable