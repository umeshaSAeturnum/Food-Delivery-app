package com.example.foodapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodapp.R
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Order
import com.example.foodapp.db.relations.UserCartItemsFood
import com.example.foodapp.ui.viewModels.CartViewModel
import com.example.foodapp.ui.viewModels.FoodViewModel
import com.example.foodapp.ui.viewModels.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

const val TAG_ORDER_FRAGMENT = "Order Fragment"
const val EST_TIME = 30 * 60 * 1000
@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_order) {
    private val viewModelCart: CartViewModel by viewModels()
    private val viewModelOrder: OrderViewModel by viewModels()
    val args: OrderFragmentArgs by navArgs()
    private var totToPay = 0.0
    private lateinit var estimated_time : String
    private lateinit var current_date: String
    private var uid : Int = 0
    private var name : String = ""
    private var address : String = ""
    private var phone : String = ""
    private var orderId :Long = 0
    private var cId :Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val checkItems = args.checkList
        uid = checkItems.last().uid
        getCurrentDate()

        Log.d(TAG_ORDER_FRAGMENT," order $checkItems")
        calcTotPrice(checkItems)
        price_or.text = "Rs.$totToPay"


        etName_or.setText(checkItems.last().name)
        etPhone_or.setText(checkItems.last().phone)
        etAddress_or.setText(checkItems.last().address)

        btnPayment_or.setOnClickListener {
            Log.d(TAG_ORDER_FRAGMENT," order items clicked $checkItems")
            addOrder(checkItems)

        }



    }

    private fun addOrder(items: MutableList<UserCartItemsFood>){

        name = etName_or.text.toString()
        address = etAddress_or.text.toString()
        phone = etPhone_or.text.toString()

        val order = Order(0,current_date,estimated_time,"Pending",name,address,phone, totToPay,uid)
        viewModelOrder.insertOrder(order)

        var cartItem : CartItem
        viewModelOrder.orderId.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
           val oId = it
            Log.d(TAG_ORDER_FRAGMENT,"$oId")
            updateCartItems(oId,items)
            Snackbar.make(requireView(),"Order Placed successfully", Snackbar.LENGTH_SHORT).show()
//            findNavController().navigate(
//                R.id.action_orderFragment_to_myOrderFragment
//            )

        })



    }

    private fun getCurrentDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            var est_time = current.plus(Duration.of(30,ChronoUnit.MINUTES))

            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")
            Log.d(TAG_ORDER_FRAGMENT, "estimated time ${est_time.format(formatter)}")
            current_date =  current.format(formatter)
            estimated_time = est_time.format(formatter)
            Log.d("answer",current_date)
        } else {

            var date = Date()
            var est_time = date.time + EST_TIME
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mm")
            current_date = formatter.format(date)
            estimated_time = formatter.format(est_time)
            Log.d("answer",current_date)
            Log.d(TAG_ORDER_FRAGMENT, "estimated time ${formatter.format(est_time)}")
        }
    }

    private fun calcTotPrice(checkItems : MutableList<UserCartItemsFood>){
        totToPay = 0.0
        for(item in checkItems){
            totToPay += item.totPrice
        }

    }

    private fun updateCartItems(orderId : Long, items : MutableList<UserCartItemsFood>){
        var cartItem : CartItem
        Log.d(TAG_ORDER_FRAGMENT,"$orderId")
        Log.d(TAG_ORDER_FRAGMENT,"order items inside method $items")
        for(item in items){
            cartItem = CartItem(
               item.cId,
               item.qty,
               orderId.toInt(),
               item.uid,
               item.fid,
               item.totPrice
           )
            viewModelCart.addUpdateItemToCart(cartItem)
        }
    }
}