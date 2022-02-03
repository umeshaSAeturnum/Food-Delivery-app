package com.example.foodapp.db.relations

data class CartItemFood(
    val cId : Int,
    var qty : Int,
    val oId : Int,
    val totPrice : Double,
    val fid : Int,
    val fname: String
)
