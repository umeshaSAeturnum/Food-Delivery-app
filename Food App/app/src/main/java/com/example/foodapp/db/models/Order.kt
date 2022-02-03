package com.example.foodapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
    val oId : Int,
    val date : String,
    val estTime : String,
    var status : String,
    val rName : String,
    val rAddress: String,
    val rPhone :String,
    val totPrice : Double,
    val uid : Int,

) : Serializable
