package com.example.foodapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.models.User
import com.example.foodapp.other.OrderingUtlity
import com.example.foodapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

   fun insertUser(user : User) = viewModelScope.launch {
       mainRepository.insertUser(user)
   }

    fun hashPassword(password : String) : String{
       return  OrderingUtlity.passwordTomd5Hash(password)
    }

    fun validateEmail(email: String) : Boolean{
        val EMAIL_ADDRESS_PATTERN = Pattern.compile( "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")

      return   EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun getUserBYEmailPassword(email : String, password : String) : LiveData<User>{
        val hashPassword = hashPassword(password)
          return mainRepository.getUserByEmailPw(email,hashPassword)

    }

    fun loginUser(user : User, email: String, password: String) : Boolean{
        val hashedPassword = hashPassword(password)
        user?.let {
            return user.password == hashedPassword && user.email == email
        }
        return false

    }

    fun getUserById(uid : Int) : LiveData<User>{
        return mainRepository.getUserById(uid)
    }

     fun updateUser(user : User) = viewModelScope.launch {
         Log.d("view model","$user")
         mainRepository.updateUser(user)
     }

    fun getUserByEmail(email: String) : LiveData<User>{
        return mainRepository.getUserByEmail(email)
    }

}