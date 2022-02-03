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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.relations.CheckItems
import com.example.foodapp.db.relations.UserCartItemsFood
import com.example.foodapp.other.Constants
import com.example.foodapp.ui.adapters.CartAdapter
import com.example.foodapp.ui.adapters.FoodAdapter
import com.example.foodapp.ui.viewModels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_food.*
import kotlinx.android.synthetic.main.fragment_shopping_cart.*
import javax.inject.Inject

const val TAG_SHOPPING_CART = "Shopping cart fragment"
@AndroidEntryPoint
class ShoppingCartFragment : Fragment(R.layout.fragment_shopping_cart),CartAdapter.EventListenerCart {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModelCart: CartViewModel by viewModels()
    private var items = mutableListOf<UserCartItemsFood>()
    private var checkItems  = CheckItems()
    lateinit var cartAdapter: CartAdapter

    private var uid : Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        getCartItemsWithFood()


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = cartAdapter.differ.currentList[position]
                val cartItem : CartItem
                item.apply {
                    cartItem = CartItem(cId, qty, oId, uid, fid, totPrice)
                }

                viewModelCart.deleteCartItem(cartItem)
                Snackbar.make(view, "Item remove from the cart successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        viewModelCart.addUpdateItemToCart(cartItem)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvShoppingCart)
        }

        btnCheckout_cv.setOnClickListener {
            items = cartAdapter.getCheckedList()

            checkItems.clear()
            if(items.size != 0) {
                for (item in items) {
                    checkItems.add(item)
                }
                Log.d(TAG_SHOPPING_CART, "items $checkItems")
                val action =
                    ShoppingCartFragmentDirections.actionShoppingCartToOrderFragment(checkItems)
                findNavController().navigate(
                    action
                )
            }

            else
                Snackbar.make(requireView(), "No items selected", Snackbar.LENGTH_SHORT).show()
        }

        btnContinue_cv.setOnClickListener {
            findNavController().navigate(
                R.id.action_shoppingCart_to_category
            )
        }


    }

    private fun getCartItemsWithFood(){
        uid =sharedPreferences.getInt(Constants.KEY_USER_ID,0)
        viewModelCart.getCartItemsByUserWithFood(uid).observe(viewLifecycleOwner, Observer { data->
            Log.d(TAG_SHOPPING_CART,"${data.size}")
            setVisibility(data.size)
            cartAdapter.differ.submitList(data)
        })
    }

    private fun setUpRecycleView(){
        cartAdapter = CartAdapter(this)
        rvShoppingCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setVisibility(size : Int){
        if(size ==0){
            findNavController().navigate(
                R.id.action_shoppingCart_to_emptyCartFragment
            )
        }

    }

    override fun updateItems(item : CartItem){
        viewModelCart.addUpdateItemToCart(item)
    }

    override fun deleteItems(item : CartItem){
        viewModelCart.deleteCartItem(item)
    }


}