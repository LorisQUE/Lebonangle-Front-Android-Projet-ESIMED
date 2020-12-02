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

class PicturesAdapter(pContext: Context, pPictures: PicturesJson): RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder>() {

    val context:Context = pContext
    val pictures:PicturesJson = pPictures

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.picture_row, parent, false)
        return PicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {

        Picasso.get().load(BASE_URL + pictures[position].contentUrl).into(holder.imgViewer);

    }


    class PicturesViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val imgViewer:ImageView = row.findViewById(R.id.imgViewerPicture)
    }

}