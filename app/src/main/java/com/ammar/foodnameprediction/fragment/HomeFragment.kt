package com.ammar.foodnameprediction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ammar.foodnameprediction.R

class HomeFragment: Fragment() {

    private lateinit var tvDescription: TextView
    private lateinit var tvHome: TextView

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.home_fragment, container, false)

        tvDescription = view.findViewById<TextView>(R.id.tvHomeDesc)
        tvHome = view.findViewById<TextView>(R.id.tvHome)

        return view
    }
}