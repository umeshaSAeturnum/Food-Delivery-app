package com.example.foodapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.db.FoodWithCartItems
import com.example.foodapp.db.models.Order
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.User
import com.example.foodapp.db.relations.CartItemFood
import com.example.foodapp.db.relations.UserCartItemsFood
import com.example.foodapp.db.relations.UserWithOrder

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(cartItem: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order) : Long

    @Transaction
    @Query("SELECT * FROM `User` WHERE uid = :uid")
    fun getOrdersByUser(uid : Int) : LiveData<List<UserWithOrder>>

    @Query("SELECT * FROM `Order` WHERE oId = :oid")
    fun getOrderById(oid : Int): LiveData<Order>

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Transaction
    @Query("SELECT * FROM User u INNER JOIN CartItem c ON u.uid = c.uid INNER JOIN Food f ON c.fid = f.fid WHERE u.uid = :uid AND oId=0")
    fun getCartItemsByUserWithFood(uid : Int): LiveData<List<UserCartItemsFood>>

    @Transaction
    @Query("SELECT * FROM CartItem c INNER JOIN Food f ON c.fid = f.fid WHERE oId = :oId")
    fun getCartItemsByOrderId(oId:Int) : LiveData<List<CartItemFood>>

    @Query("SELECT * FROM CartItem WHERE uid = :uid AND oId = 0")
    fun getCartItemsByUserId(uid: Int) : LiveData<List<CartItem>>



}