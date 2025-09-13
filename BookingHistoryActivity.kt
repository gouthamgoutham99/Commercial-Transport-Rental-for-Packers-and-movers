package com.simats.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class BookingHistoryActivity : AppCompatActivity() {

    private lateinit var bookingListLayout: LinearLayout
    private val phpUrl = "http://10.0.2.2:8080/truck_rental/get_bookings.php" // Emulator local

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)

        bookingListLayout = findViewById(R.id.bookingListLayout)

        fetchBookingHistory()
    }

    private fun fetchBookingHistory() {
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, phpUrl, null,
            { response: JSONArray ->
                if (response.length() == 0) {
                    val noBooking = TextView(this).apply {
                        text = "No bookings available."
                        setPadding(16, 16, 16, 16)
                    }
                    bookingListLayout.addView(noBooking)
                } else {
                    for (i in 0 until response.length()) {
                        val booking = response.getJSONObject(i)
                        addBookingToLayout(booking)
                    }
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )

        queue.add(jsonArrayRequest)
    }

    private fun addBookingToLayout(booking: JSONObject) {
        val textView = TextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)

            val orderId = booking.optString("order_id", "N/A")
            val username = booking.optString("username", "N/A")
            val phone = booking.optString("phoneno", "N/A")
            val truckName = booking.optString("truck_name", "N/A")
            val truckStyle = booking.optString("truck_style", "N/A")
            val truckType = booking.optString("truck_type", "N/A")
            val truckColor = booking.optString("truck_color", "N/A")
            val truckPrice = booking.optString("truck_price", "N/A")
            val address = booking.optString("address", "N/A")
            val startDate = booking.optString("start_date", "N/A")
            val endDate = booking.optString("end_date", "N/A")
            val pickupTime = booking.optString("pickup_time", "N/A")
            val dropTime = booking.optString("drop_time", "N/A")

            text = """
            Order ID: $orderId
    Username: $username
    Phone: $phone
    Truck: $truckName
    Style: $truckStyle
    Type: $truckType
    Color: $truckColor
    Price: ₹$truckPrice
    Address: $address
    Start: $startDate $pickupTime
    End: $endDate $dropTime
""".trimIndent()

        }

        bookingListLayout.addView(textView)
    }
}
