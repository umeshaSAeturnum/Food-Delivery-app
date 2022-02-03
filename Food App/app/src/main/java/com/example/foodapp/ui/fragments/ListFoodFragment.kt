package com.example.foodapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.ui.adapters.FoodAdapter
import com.example.foodapp.ui.viewModels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_food.*
import javax.inject.Inject

const val TAG_LIST_FOOD = "List Food fragment"

@AndroidEntryPoint
class ListFoodFragment : Fragment(R.layout.fragment_list_food) {
    private val viewModel: FoodViewModel by viewModels()
    val args: ListFoodFragmentArgs by navArgs()

    lateinit var foodAdapter : FoodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category

        var categoryString : String = ""
        when(category){
            1 -> categoryString = "Pizza"
            2 -> categoryString = "Beverages"
            3 -> categoryString = "Appetizers"
            4 -> categoryString = "Desserts"
        }
        category_lst.text = categoryString
        setUpRecycleView()

        foodAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("food", it)
            }
            findNavController().navigate(
                R.id.action_listFood_to_detailView,
                bundle
            )
        }

        getFoodByCategory(category)

    }

    private fun getFoodByCategory(category : Int){
        viewModel.getFoodByCategory(category).observe(viewLifecycleOwner, Observer { data ->
                foodAdapter.differ.submitList(data)
        })
    }

    private fun setUpRecycleView(){
      foodAdapter  = FoodAdapter()
        rvFoodList.apply{
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}