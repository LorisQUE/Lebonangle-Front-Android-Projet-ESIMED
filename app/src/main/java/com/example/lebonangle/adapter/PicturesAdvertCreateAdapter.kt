package com.example.lebonangle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.lebonangle.BASE_URL
import com.example.lebonangle.R
import com.example.lebonangle.api.PicturesJson
import com.squareup.picasso.Picasso

class PicturesAdvertCreateAdapter(pContext: Context, pPictures: ArrayList<String>): RecyclerView.Adapter<PicturesAdvertCreateAdapter.PicturesAdvertCreateViewHolder>() {

    val context:Context = pContext
    val pictures:ArrayList<String> = pPictures

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesAdvertCreateViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.picture_row, parent, false)
        return PicturesAdvertCreateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PicturesAdvertCreateViewHolder, position: Int) {

        Picasso.get().load(BASE_URL + pictures[position]).into(holder.imgViewer);

    }


    class PicturesAdvertCreateViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val imgViewer:ImageView = row.findViewById(R.id.imgViewerPicture)
    }

}