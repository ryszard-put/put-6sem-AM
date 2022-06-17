package com.example.jogging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jogging.adapters.JoggingCardsAdapter
import com.example.jogging.api.JoggingApi
import com.example.jogging.models.Jogging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment(val category: String) : Fragment() {
    lateinit var adapter: JoggingCardsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getJoggingListAsync()
        val joggingRecycler: RecyclerView = inflater.inflate(R.layout.fragment_category, container, false) as RecyclerView
        adapter = JoggingCardsAdapter(mutableListOf())
        joggingRecycler.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        joggingRecycler.layoutManager = layoutManager

        adapter.setListener(context as Listener)
        return joggingRecycler
    }

    private fun getJoggingListAsync() {
        val call: Call<List<Jogging>> = JoggingApi.service.getJoggings()
        call.enqueue(object: Callback<List<Jogging>> {
            override fun onResponse(call: Call<List<Jogging>>, response: Response<List<Jogging>>) {
                val jogging = response.body()
                val filtered = jogging!!.filter{it.category == category}.toMutableList()
                when(category) {
                    "city" -> {
                        Jogging.setJoggingCity(filtered)
                        updateAdapterData(Jogging.getJoggingCity())
                    }
                    "offroad" -> {
                        Jogging.setJoggingOffroad(filtered)
                        updateAdapterData(Jogging.getJoggingOffroad())
                    }
                    else -> {}
                }
            }

            override fun onFailure(call: Call<List<Jogging>>, t: Throwable) {
//                TODO("Not yet implemented")
                println(t.stackTrace)
            }
        })
    }

    private fun updateAdapterData(data: List<Jogging>){
        adapter.setData(data)
    }
}