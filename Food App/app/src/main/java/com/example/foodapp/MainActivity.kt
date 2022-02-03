package com.example.foodapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.other.Constants
import com.example.foodapp.ui.fragments.ShoppingCartFragment
import com.example.foodapp.ui.viewModels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.action_notification_icon.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val TAG_MAIN_ACTIVITY = "Main Activity"

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModelCart: CartViewModel by viewModels()
    var uid : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {

                        R.id.category, R.id.profile, R.id.shoppingCart, R.id.orderFragment, R.id.myOrderFragment, R.id.viewMyOrderFragment, R.id.listFood, R.id.detailView ->
                            bottomNavigationView.visibility = View.VISIBLE



                    }
                }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        val menuItem  = menu.findItem(R.id.action_cart)
        var itemCount = 0

        menuItem.setActionView(R.layout.action_notification_icon)

       val cartBadge : TextView = menuItem.actionView.findViewById(R.id.cart_badge)
        uid =sharedPreferences.getInt(Constants.KEY_USER_ID,0)

        viewModelCart.getCartItemsByUserId(uid).observe(this, Observer { data ->
            Log.d(TAG_MAIN_ACTIVITY, "count ${data.size}")
            itemCount  = data.size
            Log.d(TAG_MAIN_ACTIVITY, "count $itemCount")
            cartBadge.text = itemCount.toString()
            if(itemCount == 0){
                cartBadge.visibility = View.GONE
            }
            else
                cartBadge.visibility = View.VISIBLE

            menuItem.actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_cart){
            val navController = Navigation.findNavController(this,R.id.navHostFragment)
            navController.navigate(R.id.shoppingCart)
            return true
        }

        return super.onOptionsItemSelected(item)
   }


}