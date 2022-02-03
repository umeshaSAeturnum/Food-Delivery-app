package com.example.foodapp.repositories

import com.example.foodapp.db.dao.FoodDao
import com.example.foodapp.db.dao.OrderDao
import com.example.foodapp.db.dao.UserDao
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Food
import com.example.foodapp.db.models.Order
import com.example.foodapp.db.models.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    val foodDao: FoodDao,
    val userDao: UserDao,
    val orderDao: OrderDao
){

    //functions to handle foods
    suspend fun insertFood(food: Food) =foodDao.insertFood(food)

    fun getFoodByCategory(category: Int) = foodDao.getFoodByCategory(category)


    //functions to handle orders
    fun getOrderById(oid: Int) = orderDao.getOrderById(oid)

    fun getOrdersByUser(uid : Int) = orderDao.getOrdersByUser(uid)

    suspend fun insertOrder(order: Order) : Long = orderDao.insertOrder(order)

    fun getCartItemsByUserId(uid: Int) = orderDao.getCartItemsByUserId(uid)

    //functions to handle cart
    suspend fun upsertCartItem(cartItem: CartItem) = orderDao.upsertCartItem(cartItem)

    fun getCartItemsByUser(uid : Int) = userDao.getCartItemsByUser(uid)

    suspend fun deleteCartItem(cartItem: CartItem) = orderDao.deleteCartItem(cartItem)

    fun getCartItemsByUserWithFood(uid : Int) = orderDao.getCartItemsByUserWithFood(uid)

    fun getCartItemsByOrderId(oId : Int) = orderDao.getCartItemsByOrderId(oId)


    //functions to handle users
    suspend fun updateUser(user : User) = userDao.updateUser(user)

    fun getUserByEmailPw(email : String, password : String) = userDao.getUserByEmailPw(email,password)

    fun getUserById(uid : Int) = userDao.getUserById(uid)

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    fun getUserByEmail(email : String) = userDao.getUserByEmail(email)

}