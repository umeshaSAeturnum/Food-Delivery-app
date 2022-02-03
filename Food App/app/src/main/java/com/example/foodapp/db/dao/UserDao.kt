package com.example.foodapp.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.foodapp.db.models.User
import com.example.foodapp.db.relations.UserwithCartItem

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun getUserByEmailPw(email : String, password :String) : LiveData<User>

    @Query("SELECT * FROM User WHERE uid = :id")
    fun getUserById(id : Int): LiveData<User>

    @Update
    suspend fun updateUser(user : User)

    @Transaction
    @Query("SELECT * FROM User WHERE uid = :uid")
    fun getCartItemsByUser(uid : Int) : LiveData<List<UserwithCartItem>>

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email : String) : LiveData<User>

}