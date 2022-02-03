package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.db.models.User
import com.example.foodapp.ui.viewModels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*

const val TAG_REGISTER = "Registration Fragment"
@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var name : String
    private lateinit var phone : String
    private lateinit var email : String
    private lateinit var address : String
    private lateinit var password : String
    private lateinit var conpassword : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignup_s.setOnClickListener {
            initializeValues()
            if(validateRegister()){
                checkExistingUsers()
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
    private fun initializeValues(){
        name = etName_s.text.toString()
        phone = etPhone_s.text.toString()
        email = etEmail_s.text.toString()
        address = etAddress_s.text.toString()
        password = etPassword_s.text.toString()
        conpassword = etconpassword_s.text.toString()
    }

   private  fun validateRegister():Boolean{

        if(name.isEmpty()|| phone.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || conpassword.isEmpty()) {
            Snackbar.make(
                requireView(),
                "Please fill all the required fields",
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
        if(password  != conpassword) {
            Snackbar.make(
                requireView(),
                "Password and Confirm Password are not the same",
                Snackbar.LENGTH_LONG
            ).show()

            return false
        }

        if(!viewModel.validateEmail(email)){
            Snackbar.make(
                requireView(),
                "Please Enter a valid email",
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
       return true
    }

    private fun checkExistingUsers(){
        viewModel.getUserByEmail(email).observe(viewLifecycleOwner, Observer { user->

            if (user == null) {
                Log.d(TAG_REGISTER, "user null")
                register()
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment
                )
                Snackbar.make(requireView(),"Registered Successfully",Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(requireView(), "An account for the given email already exist", Snackbar.LENGTH_LONG).show()
        }
        )
    }


    private fun register(){

        val hashedPassword = viewModel.hashPassword(password)
        val user = User(0,name,phone,address,email,hashedPassword)
        viewModel.insertUser(user)

    }



}