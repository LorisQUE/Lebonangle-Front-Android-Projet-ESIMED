package com.example.lebonangle.adapter

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lebonangle.R
import com.example.lebonangle.api.AdvertsJson
import com.example.lebonangle.api.AdvertsJsonItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AdvertsAdapter(pContext:Context, pAdvert: AdvertsJson):RecyclerView.Adapter<AdvertsAdapter.AdvertsViewHolder>() {

    val context:Context = pContext
    val adverts:AdvertsJson = pAdvert
    val myFormat = "dd/MM/yyyy" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertsViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.advert_row, parent, false)
        return AdvertsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return adverts.size
    }

    override fun onBindViewHolder(holder: AdvertsViewHolder, position: Int) {

        println(adverts[position])
        val id = "#" + adverts[position].id.toString()
        val price = adverts[position].price.toString() + "€"
        holder.txtId.setText(id)
        holder.txtTitle.setText(adverts[position].title)
        holder.txtAuteur.setText(adverts[position].author)
        holder.txtMail.setText(adverts[position].email)
        holder.txtPrice.setText(price)
        holder.txtPublishedAt.setText(adverts[position].publishedAt)

        holder.txtContent.setText(adverts[position].content)
        println("ATTENTION CATEGORY DE LADVERT ICI" + adverts[position].category.toString())

        holder.cardview.setOnClickListener(View.OnClickListener {

            println("DEBUG CLICKED")

            //var intent = Intent(context, OperationEditActivity::class.java)
            //intent.putExtra("currentOperation", currentOperation)
            //startActivity(context, intent, null)

        })
    }


    class AdvertsViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val cardview:CardView = row.findViewById(R.id.cardViewAdverts)
        val txtId:TextView = row.findViewById(R.id.txtViewAdvertsId)
        val txtTitle:TextView = row.findViewById(R.id.txtViewAdvertsTitle)
        val txtAuteur:TextView = row.findViewById(R.id.txtViewAdvertsAuthor)
        val txtMail:TextView = row.findViewById(R.id.txtViewAdvertsMail)
        val txtPrice:TextView = row.findViewById(R.id.txtViewAdvertsPrice)
        val txtPublishedAt:TextView = row.findViewById(R.id.txtViewAdvertsPublishedAt)
        val txtContent:TextView = row.findViewById(R.id.txtViewAdvertsContent)
    }
}