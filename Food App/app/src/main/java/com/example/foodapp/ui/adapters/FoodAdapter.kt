package com.example.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.db.models.CartItem
import com.example.foodapp.db.models.Food
import kotlinx.android.synthetic.main.food_item.view.*
import javax.inject.Inject
import javax.inject.Singleton


class FoodAdapter constructor() : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    inner class ViewHolder(news_item: View): RecyclerView.ViewHolder(news_item) {}

    private val differCallBack = object : DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.fid == newItem.fid
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.food_item,
                parent,false
            )
        )

    }
    private var onItemClickListener: ((Food) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(food.img)
                .into(imageview_fi)
            foodName_cv.text = food.fname
            foodPrice_cv.text = "Rs. ${food.price.toDouble()}"

            setOnClickListener {
                onItemClickListener?.let { it(food) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Food) -> Unit) {
        onItemClickListener = listener
    }

}