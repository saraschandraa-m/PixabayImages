package com.saraschandraa.pixabayusers.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saraschandraa.pixabayusers.R
import com.saraschandraa.pixabayusers.databinding.CellSportsBinding
import com.saraschandraa.pixabayusers.model.Sports

class SportsListAdapter(val context: Context, private val sportsList: ArrayList<Sports>) :
    RecyclerView.Adapter<SportsListAdapter.SportsListViewHolder>(), SportsListItemClickListener {

    class SportsListViewHolder(var view: CellSportsBinding) : RecyclerView.ViewHolder(view.root)

    fun updateList(newSportsList: List<Sports>) {
        sportsList.addAll(newSportsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsListViewHolder {
        return SportsListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.cell_sports, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return sportsList.size
    }

    override fun onBindViewHolder(holder: SportsListViewHolder, position: Int) {
        holder.view.sports = sportsList[position]
        holder.view.listener = this
    }

    override fun onClick(view: View) {
        for (sport in sportsList) {
            if (view.tag == sport.largeImageURL) {
                context.startActivity(Intent(context, DetailActivity::class.java).apply {
                    putExtra("SPORT", sport)
                })
            }
        }
    }
}