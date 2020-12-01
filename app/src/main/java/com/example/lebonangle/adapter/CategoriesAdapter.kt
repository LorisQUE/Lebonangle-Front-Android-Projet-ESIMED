package com.example.lebonangle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lebonangle.R
import com.example.lebonangle.api.CategoriesJson

class CategoriesAdapter(pContext:Context, pCategories:CategoriesJson):RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    val context:Context = pContext
    val categories:CategoriesJson = pCategories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.category_row, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        println(categories[position])
        val id = "#" + categories[position].id.toString()
        holder.txtId.setText(id)
        holder.txtName.setText(categories[position].name)
    }


    class CategoriesViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val txtId:TextView = row.findViewById(R.id.txtViewCategoriesId)
        val txtName:TextView = row.findViewById(R.id.txtViewCategoriesName)
    }
}