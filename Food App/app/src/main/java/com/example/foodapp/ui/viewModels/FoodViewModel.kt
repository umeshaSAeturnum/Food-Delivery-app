package com.example.foodapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.models.Food
import com.example.foodapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
    fun insertFood(food: Food) = viewModelScope.launch {
        mainRepository.insertFood(food)
    }

    fun getFoodByCategory(category : Int) = mainRepository.getFoodByCategory(category)
}