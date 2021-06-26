package com.cahyana.asep.basicrxjavakotlin.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "username")
    val userName: String
)
