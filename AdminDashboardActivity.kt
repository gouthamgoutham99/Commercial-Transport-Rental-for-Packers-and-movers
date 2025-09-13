package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var btnViewTrucks: Button
    private lateinit var btnBookingHistory: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        btnViewTrucks = findViewById(R.id.btnViewTrucks)
        btnBookingHistory = findViewById(R.id.btnBookingHistory)
        btnLogout = findViewById(R.id.btnLogout)

        btnViewTrucks.setOnClickListener {
            startActivity(Intent(this, AvailableTrucksActivity::class.java))
        }

        btnBookingHistory.setOnClickListener {
            startActivity(Intent(this, BookingHistoryActivity::class.java))
        }

        btnLogout.setOnClickListener {
            // Return to login screen
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
