package com.example.foodapp.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.foodapp.MainActivity
import com.example.foodapp.R
import com.example.foodapp.UserActivity
import com.example.foodapp.db.models.User
import com.example.foodapp.other.Constants
import com.example.foodapp.ui.viewModels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


const val TAG_PROFILE = "Profile fragment"
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: UserViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var userC: User
    var uid : Int  = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfileInfo()

        btnUpdate_l.setOnClickListener {
            updateUserProfile()
            Snackbar.make(requireView(),"Profile updated successfully", Snackbar.LENGTH_SHORT).show()

        }

        btnLogout_l.setOnClickListener {
            writeUserDataToSharedPref()
            val intent = Intent(this@ProfileFragment.requireContext(), UserActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
//                val navOptions = NavOptions.Builder()
//                    .setPopUpTo(R.id.profile, true)
//                    .build()
//                findNavController().navigate(
//                    R.id.action_profile_to_login,
//                    savedInstanceState,
//                    navOptions
//                )
        }
    }

    private fun getProfileInfo(){
        uid =sharedPreferences.getInt(Constants.KEY_USER_ID,0)

        Log.d(TAG_PROFILE, "uid $uid")

        if(uid != 0) {
            viewModel.getUserById(uid).observe(viewLifecycleOwner, Observer { user ->
                userC = user
                name_pr.setText(user.name)
                phone_pr.setText(user.phone)
                address_pr.setText(user.address)
                email_pr.setText(user.email)
            })
        }
    }

    private fun updateUserProfile(){
        Log.d(TAG_PROFILE,"$userC")
        val name = name_pr.text.toString()
        val phone = phone_pr.text.toString()
        val email = email_pr.text.toString()
        val address = address_pr.text.toString()
        val password = userC.password

        val user = User(uid,name, phone, address, email,password)

        viewModel.updateUser(user)
    }

    private fun writeUserDataToSharedPref(){
        sharedPreferences.edit()
                .putInt(Constants.KEY_USER_ID, 0)
                .putBoolean(Constants.KEY_IS_LOGIN,false)
                .apply()
    }

}