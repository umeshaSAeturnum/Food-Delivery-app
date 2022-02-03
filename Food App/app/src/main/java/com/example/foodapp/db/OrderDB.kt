package com.example.foodapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.db.dao.FoodDao
import com.example.foodapp.db.dao.OrderDao
import com.example.foodapp.db.dao.UserDao
import com.example.foodapp.db.models.*

@Database(
    entities =[User::class, Food::class, CartItem::class, Order::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class OrderDB : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun getOrderDao() : OrderDao
    abstract fun getFoodDao() : FoodDao
}