package com.example.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.db.relations.CartItemFood
import kotlinx.android.synthetic.main.orderdetail_item.view.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemAdapter @Inject constructor(): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    inner class ViewHolder(order_item: View): RecyclerView.ViewHolder(order_item) {}

    private val differCallBack = object : DiffUtil.ItemCallback<CartItemFood>(){
        override fun areItemsTheSame(oldItem: CartItemFood, newItem: CartItemFood): Boolean {
            return oldItem.fid == newItem.fid
        }

        override fun areContentsTheSame(oldItem: CartItemFood, newItem: CartItemFood): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.orderdetail_item,
                parent,false
            )
        )

    }
    private var onItemClickListener: ((CartItemFood) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemView.apply {
            fname_odi.text = item.fname
            qty_odi.text = item.qty.toString()
            price_odi.text = item.totPrice.toString()

            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (CartItemFood) -> Unit) {
        onItemClickListener = listener
    }
}