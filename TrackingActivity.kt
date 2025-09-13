package com.simats.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class TrackingActivity : AppCompatActivity() {

    private lateinit var orderIdInput: EditText
    private lateinit var trackButton: Button
    private lateinit var trackingSteps: LinearLayout
    private lateinit var stepBooking: TextView
    private lateinit var stepPlanning: TextView
    private lateinit var stepOnWay: TextView
    private lateinit var stepReached: TextView

    private val client = OkHttpClient()
    private val baseUrl = "http://10.0.2.2:8080/truck_rental/track_order.php" // <-- replace with your server IP or domain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        orderIdInput = findViewById(R.id.orderIdInput)
        trackButton = findViewById(R.id.trackButton)
        trackingSteps = findViewById(R.id.trackingSteps)
        stepBooking = findViewById(R.id.stepBooking)
        stepPlanning = findViewById(R.id.stepPlanning)
        stepOnWay = findViewById(R.id.stepOnWay)
        stepReached = findViewById(R.id.stepReached)

        trackButton.setOnClickListener {
            val orderId = orderIdInput.text.toString().trim()

            if (orderId.isNotEmpty()) {
                fetchOrderStatus(orderId)
            } else {
                Toast.makeText(this, "Please enter Order ID", Toast.LENGTH_SHORT).show()
                trackingSteps.visibility = LinearLayout.GONE
            }
        }
    }

    private fun fetchOrderStatus(orderId: String) {
        val url = "$baseUrl?order_id=$orderId"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@TrackingActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                if (responseData != null) {
                    try {
                        val json = JSONObject(responseData)
                        val status = json.getString("status")

                        runOnUiThread {
                            updateTrackingSteps(status)
                        }

                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@TrackingActivity, "Invalid response", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun updateTrackingSteps(status: String) {
        trackingSteps.visibility = LinearLayout.VISIBLE

        // Reset all steps
        stepBooking.text = "⚪ Booking Completed"
        stepPlanning.text = "⚪ Order Under Planning"
        stepOnWay.text = "⚪ On the Way"
        stepReached.text = "⚪ Reached Destination"

        when (status) {
            "booking_completed" -> {
                stepBooking.text = "✅ Booking Completed"
            }
            "under_planning" -> {
                stepBooking.text = "✅ Booking Completed"
                stepPlanning.text = "✅ Order Under Planning"
            }
            "on_the_way" -> {
                stepBooking.text = "✅ Booking Completed"
                stepPlanning.text = "✅ Order Under Planning"
                stepOnWay.text = "✅ On the Way"
            }
            "reached" -> {
                stepBooking.text = "✅ Booking Completed"
                stepPlanning.text = "✅ Order Under Planning"
                stepOnWay.text = "✅ On the Way"
                stepReached.text = "✅ Reached Destination"
            }
            "not_found" -> {
                trackingSteps.visibility = LinearLayout.GONE
                Toast.makeText(this, "Order ID not found!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Unknown status: $status", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
