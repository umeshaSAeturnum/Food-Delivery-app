package com.example.foodapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.FtsOptions
import com.example.foodapp.R
import com.example.foodapp.db.models.Order
import com.example.foodapp.db.models.User
import com.example.foodapp.db.relations.CartItemFood
import com.example.foodapp.db.relations.UserWithOrder
import com.example.foodapp.ui.adapters.FoodAdapter
import com.example.foodapp.ui.adapters.ItemAdapter
import com.example.foodapp.ui.viewModels.OrderViewModel
import com.example.foodapp.ui.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_food.*
import kotlinx.android.synthetic.main.fragment_view_my_order.*
import kotlinx.android.synthetic.main.orderdetail_item.*
import javax.inject.Inject
const val TAG_MY_ORDER_VIEW = "My Order View Fragment"
@AndroidEntryPoint
class ViewMyOrderFragment : Fragment(R.layout.fragment_view_my_order) {
    private val viewModel: OrderViewModel by viewModels()
    val args: ViewMyOrderFragmentArgs by navArgs()
    @Inject
    lateinit var itemAdapter: ItemAdapter
    private lateinit var order: Order
    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order = args.order
        user = args.user

        date_orv.text  = order.date
        estTime_orv.text = order.estTime
        address_orv.text = "${order.rName}\n${order.rAddress}\n${order.rPhone}"
        setUpRecycleView()
        getOrderItems()

    }

    private fun getOrderItems(){
        viewModel.getCartItemsByOrderId(order.oId).observe(viewLifecycleOwner, Observer { data ->
            itemAdapter.differ.submitList(data)
            val total = calcTotPrice(data)
            Log.d(TAG_MY_ORDER_VIEW,"$total")
            total_orv.text = "Rs. $total"
        })
    }

    private fun setUpRecycleView(){
      //  itemAdapter  = ItemAdapter()
        rvOrder_items_orv.apply{
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun calcTotPrice(items : List<CartItemFood>) : Double{
        var total = 0.0
        for(item in items){
            total += item.totPrice
        }
        return total
    }
}