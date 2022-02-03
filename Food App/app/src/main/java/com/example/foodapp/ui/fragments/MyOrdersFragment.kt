package com.example.foodapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.db.models.User
import com.example.foodapp.other.Constants
import com.example.foodapp.ui.adapters.FoodAdapter
import com.example.foodapp.ui.adapters.OrderAdapter
import com.example.foodapp.ui.viewModels.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_food.*
import kotlinx.android.synthetic.main.fragment_my_order.*
import javax.inject.Inject

const val TAG_ORDER_LIST = "Order List Fragment"
@AndroidEntryPoint
class MyOrdersFragment : Fragment(R.layout.fragment_my_order) {
    private val viewModel: OrderViewModel by viewModels()
    @Inject
    lateinit var orderAdapter: OrderAdapter
    private lateinit var user:User
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid =sharedPreferences.getInt(Constants.KEY_USER_ID,0)
        getOrderByUser(uid)


    }

    private fun getOrderByUser(uId : Int){
        viewModel.getOrdersByUser(uId).observe(viewLifecycleOwner, Observer { data ->
            Log.d(TAG_ORDER_LIST, "data $data")
            Log.d(TAG_ORDER_LIST, "data count ${data.size}")
            setUpRecycleView()
                for (item in data) {
                    user = item.user
                    if(item.orders.isNotEmpty()) {
                        Log.d(TAG_ORDER_LIST, "${item.orders}")
                        Log.d(TAG_ORDER_LIST, "items count in ${item.orders.size}")
                        Log.d(TAG_ORDER_LIST, "$user")
                        orderAdapter.differ.submitList(item.orders)
                        orderAdapter.setOnItemClickListener {
                            val bundle = Bundle().apply {
                                putSerializable("order", it)
                                putSerializable("user",user)
                            }

                            findNavController().navigate(
                                R.id.action_myOrderFragment_to_viewMyOrderFragment,
                                bundle

                            )
                        }
                }
                    else{
                        findNavController().navigate(
                            R.id.action_myOrderFragment_to_emptyOrderFragment
                        )
                    }

            }
        })
    }

    private fun setUpRecycleView(){
//        orderAdapter  = OrderAdapter()
        rvOrderList.apply{
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}