package com.list_sample.realmsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmResults
import org.w3c.dom.Text

/**
 * Created by monkey on 2017/10/03.
 */
class RecyclerViewAdapter(private val dateList: RealmResults<Todo>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // ViewHolder
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var dateText: TextView = view.findViewById<TextView>(R.id.recycler_view_cell_text_view)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_cell, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = dateList[position]
        holder?.dateText?.text = item.name
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}