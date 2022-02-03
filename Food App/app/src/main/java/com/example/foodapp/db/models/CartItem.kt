package com.example.foodapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cId : Int = 0,
    var qty : Int = 0,
    var oId : Int = 0,
    val uid : Int = 0,
    val fid : Int = 0,
    var totPrice : Double = 0.00

)
