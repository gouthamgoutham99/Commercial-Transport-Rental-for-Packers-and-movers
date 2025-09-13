package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class BookNowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_now)

        val bookNowButton = findViewById<Button>(R.id.bookNowButton)

        bookNowButton.setOnClickListener {
            startActivity(Intent(this, TruckSearchActivity::class.java))
        }

        findViewById<ImageButton>(R.id.profileButton).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<ImageButton>(R.id.myBookingsButton).setOnClickListener {
            startActivity(Intent(this, UserBookingHistoryActivity::class.java))
        }

        findViewById<ImageButton>(R.id.trackingButton).setOnClickListener {
            startActivity(Intent(this, TrackingActivity::class.java))
        }

        findViewById<ImageButton>(R.id.contactUsButton).setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }

        findViewById<ImageButton>(R.id.aboutUsButton).setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }
    }
}
