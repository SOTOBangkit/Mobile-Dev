package com.ammar.foodnameprediction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ammar.foodnameprediction.R

class FoodFragment: Fragment() {

    private lateinit var tvFoodNameValue: TextView
    private lateinit var tvDescriptionValue: TextView
    private lateinit var btnRecommendedRestaurantValue: Button

    companion object {

        val FOOD_NAME = "food_name"
        val DESC = "desc"
        val RESTAURANT = "restaurant"

        fun newInstance(): FoodFragment {
            return FoodFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.food_fragment, container, false)

        tvFoodNameValue = view.findViewById<TextView>(R.id.tvFoodNameValue)
        tvDescriptionValue = view.findViewById<TextView>(R.id.tvDescriptionValue)
        btnRecommendedRestaurantValue = view.findViewById<Button>(R.id.btnRecommendedRestaurantValue)

        return view
    }

    override fun onStart() {
        super.onStart()
        val args = arguments
        if (args != null) {
            tvFoodNameValue.text = args.getString(FOOD_NAME)
            tvDescriptionValue.text = args.getString(DESC)
            btnRecommendedRestaurantValue.text = args.getString(RESTAURANT)
        }
    }

}