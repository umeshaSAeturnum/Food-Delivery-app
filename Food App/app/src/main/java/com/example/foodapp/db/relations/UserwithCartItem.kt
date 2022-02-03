package com.example.foodapp.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.User


data class UserwithCartItem(
    @Embedded val user: User,

    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )

    val cartItems : List<CartItem>
)
