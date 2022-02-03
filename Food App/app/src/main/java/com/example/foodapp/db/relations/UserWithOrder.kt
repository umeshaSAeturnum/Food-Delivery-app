package com.example.foodapp.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.foodapp.db.models.Order
import com.example.foodapp.db.models.User
import java.io.Serializable

data class UserWithOrder (
    @Embedded val user : User,

    @Relation(
        parentColumn ="uid",
        entityColumn = "uid"
    )

    val orders: List<Order>
    )