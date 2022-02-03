package com.example.foodapp.ui.fragments

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.foodapp.BaseApplication
import com.example.foodapp.MainActivity
import com.example.foodapp.R
import com.example.foodapp.db.models.User
import com.example.foodapp.other.Constants
import com.example.foodapp.other.Constants.KEY_IS_LOGIN
import com.example.foodapp.other.Constants.KEY_USER_ID
import com.example.foodapp.ui.viewModels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

const val TAG_LOGIN_FRAGMENT = "Login Fragment"
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: UserViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var email: String
    lateinit var password:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val isLoggedIn =sharedPreferences.getBoolean(Constants.KEY_IS_LOGIN,false)
        Log.d(TAG_LOGIN_FRAGMENT,"$isLoggedIn")
        if(isLoggedIn){
            val intent = Intent(this@LoginFragment.requireContext(), MainActivity::class.java)
            startActivity(intent)
//            val navOptions = NavOptions.Builder()
//                .setPopUpTo(R.id.loginFragment, true)
//                .build()
//
//            findNavController().navigate(
//                R.id.action_loginFragment_to_nav_graph,
//                savedInstanceState,
//                navOptions
//            )
        }

        btnSignup_l.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment,
            )
        }

        btnLogin.setOnClickListener {
            if(checkEmpty()){

                checkUserInDb()

            }

            else{
                Snackbar.make(requireView(),"Email and Password cannot be empty", Snackbar.LENGTH_LONG).show()
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

   private fun checkEmpty():Boolean{
       email = etName_s.text.toString()
       password = etpassword_l.text.toString()

       if(email.isEmpty() && password.isEmpty()){
           return false
       }

       return true
   }

    private fun checkUserInDb(){

        viewModel.getUserBYEmailPassword(email, password).observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(),"Email or Password is incorrect", Snackbar.LENGTH_SHORT).show()
            it?.let {
                validateLogin(it)
                writeUserDataToSharedPref(it)
                val intent = Intent(this@LoginFragment.requireContext(), MainActivity::class.java)
                startActivity(intent)

            }
        })
    }

    private fun validateLogin(user : User){

        var isValidate: Boolean = viewModel.loginUser(user, email, password)

        if(isValidate) {
            Snackbar.make(requireView(), "Login Successfully", Snackbar.LENGTH_SHORT)
                .show()

        }


    }

    private fun writeUserDataToSharedPref(user : User){

        user.uid?.let {
            sharedPreferences.edit()
                .putInt(KEY_USER_ID, it.toInt())
                .putBoolean(KEY_IS_LOGIN,true)
                .apply()
        }


    }

}