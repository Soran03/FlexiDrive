package com.example.carrentalapp.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalapp.DataModels.CarBrandModel
import com.example.carrentalapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HomeBrandsAdapter(private var brandList: List<CarBrandModel>) : RecyclerView.Adapter<HomeBrandsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandButton: ImageButton = itemView.findViewById(R.id.homeCarBrandBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sample_home_brands_btn, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBrand = brandList[position]

        Picasso.get()
            .load(currentBrand.imageUrl)
            .placeholder(R.drawable.ic_menu_gallery) // Placeholder image while loading
            .into(holder.brandButton, object : Callback {
                override fun onSuccess() {
                    Log.e("Picasso", "success")
                }

                override fun onError(e: Exception?) {
                    // Handle error (e.g., log error message)
                    Log.e("Picasso", "Error loading image", e)
                }
            })


        // Set click listener for the button if needed
        holder.brandButton.setOnClickListener {
        }
    }

    override fun getItemCount() = brandList.size

    fun updateBrands(newBrands: List<CarBrandModel>) {
        brandList = newBrands
        notifyDataSetChanged()
    }
}