package com.example.foodapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.models.Order
import com.example.foodapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
     var orderId  = MutableLiveData<Long>()

    fun insertOrder(order: Order) = viewModelScope.launch {
         val oId = mainRepository.insertOrder(order)
        orderId.postValue(oId)
    }

    fun getOrdersByUser(uId : Int) = mainRepository.getOrdersByUser(uId)

    fun getCartItemsByOrderId(oId : Int) = mainRepository.getCartItemsByOrderId(oId)
}