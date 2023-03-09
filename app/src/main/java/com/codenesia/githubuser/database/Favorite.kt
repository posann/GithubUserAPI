package com.codenesia.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username : String = "",

    @ColumnInfo(name = "urlImage")
    var urlImage : String? = null

) : Parcelable