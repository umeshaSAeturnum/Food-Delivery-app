package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user.*

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

//        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
//
//        navHostFragment.findNavController()
//            .addOnDestinationChangedListener { _, destination, _ ->
//                when (destination.id) {
//
//                    R.id.category, R.id.profile, R.id.shoppingCart, R.id.orderFragment, R.id.myOrderFragment, R.id.viewMyOrderFragment, R.id.listFood, R.id.detailView ->
//                        bottomNavigationView.visibility = View.VISIBLE
//
//                    R.id.loginFragment,R.id.registerFragment -> bottomNavigationView.visibility = View.GONE
//
//
//                }
//            }
    }
}