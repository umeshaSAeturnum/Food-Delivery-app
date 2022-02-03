package com.example.foodapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid : Int,
    val name : String,
    val phone : String,
    val address : String,
    val email : String,
    val password : String
) : Serializable