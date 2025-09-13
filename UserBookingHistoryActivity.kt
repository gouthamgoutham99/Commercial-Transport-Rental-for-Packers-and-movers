package com.simats.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class UserBookingHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_booking_history)

        recyclerView = findViewById(R.id.recyclerViewBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookingAdapter = BookingAdapter(bookingList)
        recyclerView.adapter = bookingAdapter

        // Get logged-in username from session
        val username = UserSession.getUsername(this)

        if (username != null) {
            fetchBookings(username)
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchBookings(username: String) {
        val url = "http://10.0.2.2:8080/truck_rental/fetch_user_bookings.php?username=$username"

        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val status = response.getString("status")
                    if (status == "success") {
                        val bookingsArray = response.getJSONArray("bookings")
                        bookingList.clear()

                        for (i in 0 until bookingsArray.length()) {
                            val obj = bookingsArray.getJSONObject(i)
                            val booking = Booking(
                                orderId = obj.getString("order_id"),
                                username = obj.getString("username"),
                                truckName = obj.getString("truck_name"),
                                truckStyle = obj.getString("truck_style"),
                                truckType = obj.getString("truck_type"),
                                truckColor = obj.getString("truck_color"),
                                totalFare = obj.getString("price"),
                                address = obj.getString("address"),
                                startDate = obj.getString("start_date"),
                                endDate = obj.getString("end_date"),
                                pickupTime = obj.getString("start_time")
                                // removed dropTime, replaced by address already
                            )
                            bookingList.add(booking)
                        }
                        bookingAdapter.notifyDataSetChanged()
                    } else {
                        val message = response.optString("message", "Failed to load bookings")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("Volley", "Error: ${error.message}")
                Toast.makeText(this, "Network error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }
}
