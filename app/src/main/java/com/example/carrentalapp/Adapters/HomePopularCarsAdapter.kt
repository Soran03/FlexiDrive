package com.example.carrentalapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalapp.DataModels.CarBrandModel
import com.example.carrentalapp.DataModels.CarModel
import com.example.carrentalapp.R
import com.squareup.picasso.Picasso

class HomePopularCarsAdapter(private var popularCarList: List<CarModel>) : RecyclerView.Adapter<HomePopularCarsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTv: TextView = itemView.findViewById(R.id.homePopularCarBrand)
        val modelTv: TextView = itemView.findViewById(R.id.homePopularCarModel)
        val seatsTv: TextView = itemView.findViewById(R.id.homePopularCarSeats)
        val doorsTv: TextView = itemView.findViewById(R.id.homePopularCarDoors)
        val transmissionTv: TextView = itemView.findViewById(R.id.homePopularCarTransmission)
        val addressTv: TextView = itemView.findViewById(R.id.homePopularCarAddress)

        val carImage: ImageView = itemView.findViewById(R.id.homePopularCarImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePopularCarsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sample_home_popular_car, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomePopularCarsAdapter.ViewHolder, position: Int) {
        val currentCar = popularCarList[position]

        holder.brandTv.text = currentCar.brand
        holder.modelTv.text = currentCar.model
        holder.seatsTv.text = currentCar.seats.toString()
        holder.doorsTv.text = currentCar.doors.toString()
        holder.transmissionTv.text = currentCar.transmission

        Picasso.get()
            .load(currentCar.image)
            .placeholder(R.drawable.ic_menu_gallery)
            .into(holder.carImage)

        holder.itemView.setOnClickListener{
            Toast.makeText(it.context, "${currentCar.brand} ${currentCar.model}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = popularCarList.size

    fun updateBrands(newCars: List<CarModel>) {
        popularCarList = newCars
        notifyDataSetChanged()
    }

}