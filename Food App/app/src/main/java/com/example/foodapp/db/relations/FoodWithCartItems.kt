package com.example.foodapp.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Food

data class FoodWithCartItems (
    @Embedded val food: Food,
    @Relation(
        parentColumn = "fid",
        entityColumn = "fid"
    )
    val cartItems : List<CartItem>
)