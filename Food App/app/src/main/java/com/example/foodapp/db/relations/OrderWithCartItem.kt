package com.example.foodapp.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Order


data class OrderWithCartItem (
    @Embedded val order: Order,
    @Relation(
        parentColumn ="oId",
        entityColumn ="oId"
    )

    val cartItems: List<CartItem>
    )