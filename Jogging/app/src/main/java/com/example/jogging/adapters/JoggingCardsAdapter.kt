package com.example.jogging.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jogging.Listener
import com.example.jogging.R
import com.example.jogging.models.Jogging
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL

class JoggingCardsAdapter(private val data: MutableList<Jogging>): RecyclerView.Adapter<JoggingCardsAdapter.ViewHolder>() {

    private lateinit var listener: Listener

    fun setListener(_listener: Listener) {
        listener = _listener
    }

    fun setData(arr: List<Jogging>) {
        data.apply {
            clear()
            addAll(arr)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val textView: TextView

        init {
            imageView = view.findViewById(R.id.card_image)
            textView = view.findViewById(R.id.card_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.jogging_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            Picasso.get().load(data[position].image).into(imageView);
            textView.text = data[position].title
        }
        holder.itemView.setOnClickListener {
            if (listener != null) {
                listener.itemClicked(data[position].id)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}