package com.example.foodapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val fid:Int = 0,
    val fname: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: Int = 0,
    val img: String = ""
) : Serializable
