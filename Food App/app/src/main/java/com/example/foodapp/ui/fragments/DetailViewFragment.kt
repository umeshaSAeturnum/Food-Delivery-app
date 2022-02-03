package com.example.foodapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Food
import com.example.foodapp.other.Constants
import com.example.foodapp.other.Constants.INITIAL_ORDER_ID
import com.example.foodapp.other.Constants.IS_CHECK_FALSE
import com.example.foodapp.ui.viewModels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_view.*
import javax.inject.Inject

const val TAG_DETAIL_FOOD = "DETAIL_VIEW_FRAGMENT"
@AndroidEntryPoint
class DetailViewFragment : Fragment(R.layout.fragment_detail_view) {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var items = mutableListOf<CartItem>()
    private val viewModelCart: CartViewModel by viewModels()
    val args: DetailViewFragmentArgs by navArgs()
    private var uid : Int = 0
    private var food = Food()
    private var foodId = 0
    private var quantity = 0
    private var foodPrice = 0.00
    private var totPrice = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        food = args.food
        with(food){
            Glide.with(this@DetailViewFragment)
                .load(img)
                .into(imageview_detail)
            tvFoodName_d.text = fname
            price_fd.text = "Rs. $price"
            content_detail.text = description
            foodId = fid
            foodPrice = price

        }

        getCartItemsOfUser()

        btn_minus_dtl.setOnClickListener {
            quantity = Integer.parseInt(quantity_dtl.text.toString())
            if(quantity > 0) {
                quantity--
                quantity_dtl.text = quantity.toString()
                reduceQuantity()
            }
            else{
                quantity = 0
                quantity_dtl.text = quantity.toString()
            }
            deleteCartItem()

        }

        btn_plus_dtl.setOnClickListener {
            quantity = Integer.parseInt(quantity_dtl.text.toString())
            quantity += 1
            quantity_dtl.text = quantity.toString()
            addCartItems()
        }

    }

    private fun addCartItems(){
        calculateTotPrice()
        var isExist = 0
            if(quantity > 0){
               for(item in items){
                   if(item.fid == foodId){
                       val cartItem = CartItem(item.cId,quantity,INITIAL_ORDER_ID,uid,foodId,totPrice)
                       viewModelCart.addUpdateItemToCart(cartItem)
                       isExist = 1
                       break
                   }
               }
                if(isExist == 0){
                        val cartItem = CartItem(0,quantity, INITIAL_ORDER_ID,uid,foodId,totPrice)
                        viewModelCart.addUpdateItemToCart(cartItem)
                }


            }
            if(quantity == 1)
                Snackbar.make(requireView(),"Item added to the cart Successfully",Snackbar.LENGTH_SHORT).show()

    }

    private fun getCartItemsOfUser(){
        uid =sharedPreferences.getInt(Constants.KEY_USER_ID,0)
        var cItem : CartItem
        viewModelCart.getCartItemsByUser(uid).observe(viewLifecycleOwner, Observer {data ->
            for(i in data){
                for(index in i.cartItems.indices){
                    cItem = i.cartItems[index]
                    items.add(cItem)
                    if(cItem.fid == foodId && cItem.oId == 0) {
                        quantity_dtl.text = cItem.qty.toString()

                    }
                }
            }
        })
    }

    private fun deleteCartItem(){
        if(quantity == 0){
            for(item in items){
                if(item.fid == foodId){
                    viewModelCart.deleteCartItem(item)
                    break
                }
            }
            Snackbar.make(requireView(),"No item added to the cart", Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun reduceQuantity() {
        calculateTotPrice()
        if (quantity > 0) {
            for (item in items) {
                if (item.fid == foodId) {
                    val cartItem = CartItem(item.cId, quantity, INITIAL_ORDER_ID, uid, foodId,totPrice)
                    viewModelCart.addUpdateItemToCart(cartItem)
                    break
                }

            }
        }
    }

    private fun calculateTotPrice(){
        totPrice = foodPrice * quantity
    }
}


