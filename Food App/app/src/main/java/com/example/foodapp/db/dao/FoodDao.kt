package com.example.foodapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.db.models.Food
import com.example.foodapp.db.models.User

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food : Food)

    @Query("SELECT * FROM `Food` WHERE category LIKE :category")
    fun getFoodByCategory(category : Int) : LiveData<List<Food>>

    @Query("SELECT * FROM `Food` WHERE fid = :fid")
    fun getFoodById(fid : Int): LiveData<Food>
}