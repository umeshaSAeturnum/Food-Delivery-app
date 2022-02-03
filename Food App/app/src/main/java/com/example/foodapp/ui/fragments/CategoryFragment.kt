package com.example.foodapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.db.models.Food
import com.example.foodapp.other.Constants.CATEGORY_APPETIZERS
import com.example.foodapp.other.Constants.CATEGORY_BEVERAGES
import com.example.foodapp.other.Constants.CATEGORY_DESSERTS
import com.example.foodapp.other.Constants.CATEGORY_PIZZA
import com.example.foodapp.ui.viewModels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val viewModel: FoodViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
//        insertFoodToDb()

        pizza_card.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryToListFood(CATEGORY_PIZZA)
            findNavController().navigate(
               action
            )
        }

        beverage_card.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryToListFood(CATEGORY_BEVERAGES)
            findNavController().navigate(
               action
            )
        }

        appetizer_card.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryToListFood(CATEGORY_APPETIZERS)
            findNavController().navigate(
               action
            )
        }

        dessert_card.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryToListFood(CATEGORY_DESSERTS)
            findNavController().navigate(
               action
            )
        }

    }

//    private fun insertFoodToDb(){
//        val food  = Food(0,"Popcorn Veggie","Delightful Popcorn Veggie made with carrots, mushrooms & potatoes accompanied by green chillies, onions & a layer of mozzarella & cream cheese, upon a sriracha & tomato sauce base.",1600.00,1,"https://image.shutterstock.com/image-photo/supreme-pizza-lifted-slice-1-600w-84904912.jpg")
//        viewModel.insertFood(food)
//
//        val food2 = Food(0,"Cheese Lovers","Rich tomato sauce with a triple layer of mozzarella cheese.",1700.00,1,"https://image.shutterstock.com/image-photo/hot-pizza-slice-melting-cheese-600w-378226756.jpg")
//        viewModel.insertFood(food2)
//
//        val food3 = Food(0,"Sausage Delight", "Chicken sausages &U onions with a double layer of mozzarella cheese.",1700.00,1,"https://image.shutterstock.com/image-photo/concept-horizontal-promotional-flyer-poster-600w-1248274780.jpg")
//        viewModel.insertFood(food3)
//
//        val food4 = Food(0,"Devilled Chicken","Devilled chicken in spicy sauce with doule layer of mozzarella cheese.",1800.00,1,"https://image.shutterstock.com/image-photo/pizza-resturant-menu-studio-light-600w-1612237426.jpg")
//        viewModel.insertFood(food4)
//
//        val bev1 = Food(0,"Chocolate Milkshake","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque.",300.00,2,"https://image.shutterstock.com/image-photo/chocolate-milkshake-ice-cream-whipped-600w-667618222.jpg")
//        viewModel.insertFood(bev1)
//        val bev2 = Food(0,"Strawberry Milkshake","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque.",300.00,2,"https://image.shutterstock.com/z/stock-photo-glass-of-fresh-strawberry-milkshake-smoothie-and-fresh-strawberries-on-pink-white-and-wooden-1415351087.jpg")
//        viewModel.insertFood(bev2)
//
//        val bev3 = Food(0,"Iced Coffee", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque.",350.00,2,"https://image.shutterstock.com/z/stock-photo-iced-coffee-in-a-tall-glass-with-cream-poured-over-289454174.jpg")
//        viewModel.insertFood(bev3)
//
//        val bev4 = Food(0,"Mojito","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",300.00,2,"https://image.shutterstock.com/z/stock-photo-fresh-mojito-in-glasses-on-wooden-table-1183159792.jpg")
//        viewModel.insertFood(bev4)
//
//        val appetizer1 = Food(0, "Spinash Tartlet","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",500.00,3,"https://image.shutterstock.com/z/stock-photo-appetizer-tartlets-with-spinach-feta-tomatoes-cheese-and-garlic-498542155.jpg")
//        viewModel.insertFood(appetizer1)
//
//        val appetizer2 = Food(0,"Mini corn tacos with cod filling","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque.",450.00,3,"https://image.shutterstock.com/z/stock-photo-mini-corn-tacos-with-cod-filling-1309350052.jpg")
//        viewModel.insertFood(appetizer2)
//
//        val appetizer3 = Food(0,"Deep Fried Turkish Cigar shaped rolls","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",300.00,3,"https://image.shutterstock.com/z/stock-photo-deep-fried-turkish-cigar-shaped-rolls-spring-roll-sigara-borek-1486056110.jpg")
//        viewModel.insertFood(appetizer3)
//
//        val appetizer4 = Food(0,"Indonesian style stuffed fried tofu","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",200.00,3,"https://image.shutterstock.com/z/stock-photo-indonesian-style-stuffed-fried-tofu-1866448939.jpg")
//        viewModel.insertFood(appetizer4)
//
//        val dessert1 = Food(0, "Chocolate fudged brownies with ice cream","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot",400.00,4,"https://image.shutterstock.com/z/stock-photo-a-chocolate-fudge-brownie-with-ice-cream-whipped-cream-and-a-cherry-107042897.jpg")
//        viewModel.insertFood(dessert1)
//
//        val dessert2 = Food(0,"Cheese cake with fresh berries and mint","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",400.00,4,"https://image.shutterstock.com/z/stock-photo-homemade-cheesecake-with-fresh-berries-and-mint-for-dessert-healthy-organic-summer-dessert-pie-631520744.jpg")
//        viewModel.insertFood(dessert2)
//
//        val dessert3 = Food(0,"mango panna cotta jelly and mint dessert","Li Europan lingues es membres del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot.",450.00,4,"https://image.shutterstock.com/z/stock-photo-mango-panna-cotta-with-mango-jelly-and-mint-italian-dessert-homemade-cuisine-1084115219.jpg")
//        viewModel.insertFood(dessert3)
//
//        val dessert4 = Food(0, "Chocolate dessert pudding with whipped cream","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque",400.00,4,"https://image.shutterstock.com/z/stock-photo-chocolate-dessert-pudding-with-whipped-cream-572247550.jpg")
//        viewModel.insertFood(dessert4)
//
//    }
}