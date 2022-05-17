package com.example.listdetailsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Category(val key: String, val name: String) {}

class CategoryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        val categories = listOf(
            Category("short", "Krótka trasa"),
            Category("medium", "Średnia trasa"),
            Category("long", "Długa trasa"),
            Category("forest", "W lesie"),
            Category("city", "W mieście")
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = CategoryFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_POSITION, position)
        }
        return fragment
    }
}

private const val ARG_POSITION = "position"

// Instances of this class are fragments representing a single
// object in our collection.
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            val textView = view.findViewById<TextView>(R.id.text1)
            textView.text = CategoryAdapter.categories[getInt(ARG_POSITION)].name
        }
    }
}