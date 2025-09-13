package com.simats.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(private val bookings: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.orderIdText)
        val truckNameTextView: TextView = itemView.findViewById(R.id.truckNameTextView)
        val truckStyleTextView: TextView = itemView.findViewById(R.id.truckStyleTextView)
        val truckTypeTextView: TextView = itemView.findViewById(R.id.truckTypeTextView)
        val truckColorTextView: TextView = itemView.findViewById(R.id.truckColorTextView)
        val totalFareTextView: TextView = itemView.findViewById(R.id.totalFareTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val dateRangeTextView: TextView = itemView.findViewById(R.id.dateRangeTextView)
        val pickupTimeTextView: TextView = itemView.findViewById(R.id.pickupTimeTextView) // ✅
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]

        holder.orderIdTextView.text = "Order ID: ${booking.orderId}"
        holder.truckNameTextView.text = "Truck: ${booking.truckName}"
        holder.truckStyleTextView.text = "Style: ${booking.truckStyle}"
        holder.truckTypeTextView.text = "Type: ${booking.truckType}"
        holder.truckColorTextView.text = "Color: ${booking.truckColor}"
        holder.totalFareTextView.text = "Fare: ₹${booking.totalFare}"
        holder.addressTextView.text = "Address: ${booking.address}"
        holder.dateRangeTextView.text = "Dates: ${booking.startDate} - ${booking.endDate}"
        holder.pickupTimeTextView.text = "Pickup Time: ${booking.pickupTime}" // ✅ Only pickup time
    }

    override fun getItemCount(): Int {
        return bookings.size
    }
}
