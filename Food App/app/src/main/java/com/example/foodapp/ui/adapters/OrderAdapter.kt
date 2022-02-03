package com.example.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.db.models.Order
import kotlinx.android.synthetic.main.food_item.view.*
import kotlinx.android.synthetic.main.order_item.view.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderAdapter @Inject constructor() : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    inner class ViewHolder(order_item: View): RecyclerView.ViewHolder(order_item) {}

    private val differCallBack = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.oId == newItem.oId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_item,
                parent,false
            )
        )

    }
    private var onItemClickListener: ((Order) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = differ.currentList[position]

        holder.itemView.apply {
            id_orl.text = order.oId.toString()
            price_orl.text = "Rs. ${order.totPrice}"
            status_orl.text = order.status

            setOnClickListener {
                onItemClickListener?.let { it(order) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onItemClickListener = listener
    }
}