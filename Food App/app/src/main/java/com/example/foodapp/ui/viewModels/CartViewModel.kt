package com.example.foodapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.relations.UserCartItemsFood
import com.example.foodapp.db.relations.UserwithCartItem
import com.example.foodapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    fun getCartItemsByUser(uid : Int) : LiveData<List<UserwithCartItem>>{
        return mainRepository.getCartItemsByUser(uid)
    }

    fun addUpdateItemToCart(cartItem: CartItem) = viewModelScope.launch {
        mainRepository.upsertCartItem(cartItem)
    }

    fun deleteCartItem(cartItem: CartItem) = viewModelScope.launch {
        mainRepository.deleteCartItem(cartItem)
    }

    fun getCartItemsByUserWithFood(uid : Int) : LiveData<List<UserCartItemsFood>>{
        return mainRepository.getCartItemsByUserWithFood(uid)
    }

    fun getCartItemsByUserId(uid: Int) : LiveData<List<CartItem>>{
        return mainRepository.getCartItemsByUserId(uid)
    }


}


