package com.example.foodapp.db.relations

import java.io.Serializable


data class UserCartItemsFood(
   val uid :Int,
   val name: String,
   val phone:String,
   val address: String,
   val oId : Int,
   val cId : Int,
   var qty : Int,
   var totPrice: Double,
   val fid : Int,
   val fname : String,
   val price : Double,
   val img : String,
   val category : String

): Serializable

class CheckItems : ArrayList<UserCartItemsFood>(),Serializable



