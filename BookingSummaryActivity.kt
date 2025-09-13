package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class BookingSummaryActivity : AppCompatActivity() {

    private lateinit var truckNameText: TextView
    private lateinit var truckPriceText: TextView
    private lateinit var truckStyleText: TextView
    private lateinit var truckTypeText: TextView
    private lateinit var truckColorText: TextView
    private lateinit var addressText: TextView
    private lateinit var startDateText: TextView
    private lateinit var pickupTimeText: TextView
    private lateinit var fareText: TextView
    private lateinit var proceedToPayButton: Button
    private lateinit var backArrow: ImageView

    private lateinit var username: String
    private lateinit var phone: String
    private lateinit var truckName: String
    private lateinit var truckStyle: String
    private lateinit var truckType: String
    private lateinit var truckColor: String
    private lateinit var address: String
    private lateinit var startDate: String
    private lateinit var pickupTime: String

    private var totalPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_summary)

        // UI elements
        backArrow = findViewById(R.id.backArrow)
        truckNameText = findViewById(R.id.truckName)
        truckPriceText = findViewById(R.id.truckPrice)
        truckStyleText = findViewById(R.id.truckStyle)
        truckTypeText = findViewById(R.id.truckType)
        truckColorText = findViewById(R.id.truckColor)
        addressText = findViewById(R.id.addressText)
        startDateText = findViewById(R.id.startDate)
        pickupTimeText = findViewById(R.id.pickupTime)
        fareText = findViewById(R.id.fareAmount)
        proceedToPayButton = findViewById(R.id.proceedToPayButton)

        // Intent data
        truckName = intent.getStringExtra("truckName") ?: ""
        truckStyle = intent.getStringExtra("truckStyle") ?: ""
        truckType = intent.getStringExtra("truckType") ?: ""
        truckColor = intent.getStringExtra("truckColor") ?: ""

        // Get SearchData
        startDate = SearchData.startDate.ifEmpty { intent.getStringExtra("startDate") ?: "" }
        pickupTime = SearchData.pickupTime.ifEmpty { intent.getStringExtra("pickupTime") ?: "" }
        address = SearchData.address.ifEmpty { intent.getStringExtra("address") ?: "" }

        // Distance-based pricing
        val distanceKm: Double = SearchData.distanceKm
        val pricePerKm: Double = intent.getDoubleExtra("pricePerKm", 0.0)
        totalPrice = (distanceKm * pricePerKm).toInt()

        // Debug log
        Log.d("BOOKING_DEBUG", "DistanceKm=$distanceKm, PricePerKm=$pricePerKm, Total=$totalPrice")

        val formattedPrice = "₹ %,d".format(totalPrice)

        // Set UI
        truckNameText.text = truckName
        truckPriceText.text = "₹ $pricePerKm / km"
        truckStyleText.text = "Style: $truckStyle"
        truckTypeText.text = "Type: $truckType"
        truckColorText.text = "Color: $truckColor"
        addressText.text = "Address: $address"
        startDateText.text = "Start Date: $startDate"
        pickupTimeText.text = "Pick-up Time: $pickupTime"
        fareText.text = "Fare: $formattedPrice"
        proceedToPayButton.text = "Proceed to Pay $formattedPrice"

        // Get user info
        username = UserSession.getUsername(this) ?: "Unknown"
        phone = UserSession.getPhone(this) ?: "Unknown"

        // Back
        backArrow.setOnClickListener { onBackPressed() }

        proceedToPayButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirm Payment")
                .setMessage("This is a mock payment. Proceed?")
                .setPositiveButton("Yes") { _, _ ->
                    storeBookingToBackend()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun storeBookingToBackend() {
        val url = "http://10.0.2.2:8080/truck_rental/store_booking.php"

        // Log values for debugging
        Log.d("BOOKING_DEBUG", "username=$username, phone=$phone, truck_name=$truckName, price=$totalPrice")
        Log.d("BOOKING_DEBUG", "start=$startDate, pickup=$pickupTime, address=$address")

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Toast.makeText(this, "Booking stored successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, BookingSuccessActivity::class.java))
                finish()
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                return mapOf(
                    "username" to username,
                    "phoneno" to phone,
                    "truck_name" to truckName,
                    "truck_style" to truckStyle,
                    "truck_type" to truckType,
                    "truck_color" to truckColor,
                    "truck_price" to totalPrice.toString(),
                    "address" to address,
                    "start_date" to startDate,
                    "pickup_time" to pickupTime
                )
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }
}
