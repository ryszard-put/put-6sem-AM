package com.example.jogging

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.example.jogging.api.JoggingApi
import com.example.jogging.models.Jogging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoggingListFragment : ListFragment() {
    private lateinit var joggingList: List<Jogging>
    private lateinit var adapter: ArrayAdapter<String>

    interface Listener {
        fun itemClicked(id: Long)
    }

    private lateinit var listener: Listener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1, mutableListOf())
        listAdapter = adapter
        getJoggingListAsync(inflater)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener.itemClicked(id)
    }

    private fun getJoggingListAsync(inflater: LayoutInflater) {
        val call: Call<List<Jogging>> = JoggingApi.service.getJoggings()
        call.enqueue(object: Callback<List<Jogging>> {
            override fun onResponse(call: Call<List<Jogging>>, response: Response<List<Jogging>>) {
                val jogging = response.body()
                Jogging.setJoggingList(jogging!!.toMutableList())
                updateAdapterData(jogging!!.map{it.title} as MutableList<String>)
            }

            override fun onFailure(call: Call<List<Jogging>>, t: Throwable) {
//                TODO("Not yet implemented")
                println(t.stackTrace)
            }
        })
    }

    private fun updateAdapterData(data: MutableList<String>){
        adapter.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }
}