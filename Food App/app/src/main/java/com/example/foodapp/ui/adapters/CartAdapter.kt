package com.example.foodapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.db.FoodWithCartItems
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.relations.UserCartItemsFood
import com.example.foodapp.other.Constants
import com.example.foodapp.ui.fragments.DetailViewFragment
import com.example.foodapp.ui.fragments.TAG_DETAIL_FOOD
import com.example.foodapp.ui.viewModels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_detail_view.*

const val TAG_CART_ADAPTER = "Cart Adapter"
class CartAdapter(
    eventListenerCart: EventListenerCart
): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var eventListenerCart : EventListenerCart = eventListenerCart
    private lateinit var viewModel: CartViewModel
    private var quantity = 0
    private var uid = 0
    private var foodId = 0
    private var cId = 0
    private var totPrice = 0.0
    private var foodPrice = 0.0

    private lateinit var checkedList : MutableList<UserCartItemsFood>


    inner class ViewHolder(cart_item: View) : RecyclerView.ViewHolder(cart_item) {}

    private val differCallBack = object : DiffUtil.ItemCallback<UserCartItemsFood>() {
        override fun areItemsTheSame(
            oldItem: UserCartItemsFood,
            newItem: UserCartItemsFood
        ): Boolean {
            return oldItem.cId == newItem.cId
        }

        override fun areContentsTheSame(
            oldItem: UserCartItemsFood,
            newItem: UserCartItemsFood
        ): Boolean {
            return oldItem == newItem
        }

    }



    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        checkedList = mutableListOf()
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart_item,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodAndCart = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(foodAndCart.img)
                .into(imageview_ci)
            foodName_ci.text = foodAndCart.fname
            foodPrice_ci.text = "Rs. ${foodAndCart.totPrice}"
            tvQty_ci.text = foodAndCart.qty.toString()
            uid = foodAndCart.uid
            foodId = foodAndCart.fid
            cId = foodAndCart.cId
            foodPrice = foodAndCart.price
            chkBox_ci.isChecked = false


            chkBox_ci.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    addCheckedItems(foodAndCart)
                }
                else{
                    removeCheckedItem(foodAndCart)
                }

                Log.d(TAG_CART_ADAPTER, " check $checkedList")
            }
            btn_minus_ci.setOnClickListener {
                quantity = Integer.parseInt(tvQty_ci.text.toString())
                chkBox_ci.isChecked = false
                if (quantity > 0) {
                    quantity--
                    tvQty_ci.text = quantity.toString()
                    reduceQuantity(foodAndCart,quantity)
                    foodPrice_ci.text = "Rs. ${foodAndCart.totPrice}"

                    if(quantity == 0){
                        Log.d(TAG_CART_ADAPTER,"$quantity")
                        deleteCartItem(foodAndCart,quantity)
                    }
                    if(!chkBox_ci.isChecked){
                        if(checkedList.contains(foodAndCart))
                            removeCheckedItem(foodAndCart)
                    }

                }

                Log.d(TAG_CART_ADAPTER, " minus $checkedList")


            }
            btn_plus_ci.setOnClickListener {
                chkBox_ci.isChecked = false
                quantity = Integer.parseInt(tvQty_ci.text.toString())
                quantity += 1
                tvQty_ci.text = quantity.toString()
                addCartItems(foodAndCart,quantity)
                if(!chkBox_ci.isChecked){
                    if(checkedList.contains(foodAndCart))
                        removeCheckedItem(foodAndCart)
                    Log.d(TAG_CART_ADAPTER, " add is checked false")

                }
                Log.d(TAG_CART_ADAPTER, " plus $checkedList")

            }
        }
    }

    private fun addCartItems(item : UserCartItemsFood, qty: Int){
        foodPrice = item.price
        cId = item.cId
        quantity = qty
        uid = item.uid
        foodId = item.fid
        totPrice = calculateTotPrice(quantity,foodPrice)
        if(quantity > 0){
            val cartItem = CartItem(cId,quantity,
                Constants.INITIAL_ORDER_ID,uid,foodId,totPrice)
            eventListenerCart.updateItems(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private fun addCheckedItems(item: UserCartItemsFood) {
        checkedList.add(item)
        Log.d(TAG_CART_ADAPTER,"$checkedList")
    }

    private fun removeCheckedItem(item: UserCartItemsFood){
        checkedList.remove(item)
        Log.d(TAG_CART_ADAPTER,"$checkedList")
    }

    fun getCheckedList(): MutableList<UserCartItemsFood> {

        return checkedList
    }

    private fun reduceQuantity(item :UserCartItemsFood,qty : Int) {
        foodPrice = item.price
        cId = item.cId
        quantity = qty
        uid= item.uid
        foodId = item.fid
        totPrice = calculateTotPrice(quantity,foodPrice)


        if (quantity > 0) {
            val cartItem =
                CartItem(cId, quantity, Constants.INITIAL_ORDER_ID, uid, foodId, totPrice)
            eventListenerCart.updateItems(cartItem)

        }

    }

    private fun calculateTotPrice(quantity: Int, foodPrice : Double) : Double{
        return foodPrice * quantity
    }

    private fun deleteCartItem(item : UserCartItemsFood, quantity: Int) {
        val cId = item.cId
        val totPrice = item.totPrice
        val fid = item.fid
        val uid = item.uid
        val oId = item.oId
        val cartItem = CartItem(cId,quantity,oId,uid,fid,totPrice)
        eventListenerCart.deleteItems(cartItem)


    }

    interface EventListenerCart{
        fun updateItems(item : CartItem);
        fun deleteItems(item: CartItem);
    }


}




