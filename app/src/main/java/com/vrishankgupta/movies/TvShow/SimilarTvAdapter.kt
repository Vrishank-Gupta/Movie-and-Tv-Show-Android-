package com.vrishankgupta.movies.TvShow

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso
import com.vrishankgupta.movies.R
import com.vrishankgupta.movies.TvDetail

import java.util.ArrayList

/**
 * Created by vrishankgupta on 17/01/18.
 */

class SimilarTvAdapter(private val context: Context, private val arrayList: ArrayList<Tv>) : RecyclerView.Adapter<SimilarTvAdapter.vHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vHolder {
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.similar_card,parent, false)
        return vHolder(itemView)
    }

    override fun onBindViewHolder(holder: vHolder, position: Int) {
        val movies = arrayList[position]
        holder.sim_rating.text = movies.vote_average
        holder.sim_name.text = movies.original_name
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + movies.poster_path!!).into(holder.sim_image)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class vHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var sim_image: ImageView
        internal var sim_name: TextView
        internal var sim_rating: TextView
        internal var cardView: CardView

        init {
            cardView = itemView.findViewById(R.id.sim_card)
            sim_image = itemView.findViewById(R.id.sim_image)
            sim_name = itemView.findViewById(R.id.sim_name)
            cardView.setOnClickListener(this)
            sim_rating = itemView.findViewById(R.id.sim_rating)
        }

        override fun onClick(v: View) {
            val i = Intent(context, TvDetail::class.java)
            i.putExtra("TvIntent", arrayList[position])
            context.startActivity(i)
        }
    }
}
