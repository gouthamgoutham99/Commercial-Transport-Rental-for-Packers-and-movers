package com.simats.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val username = UserSession.getUsername(this) ?: "Unknown"
        val phone = UserSession.getPhone(this) ?: "N/A"

        val usernameText = findViewById<TextView>(R.id.usernameText)
        val phoneText = findViewById<TextView>(R.id.phoneText)

        usernameText.text = "Username: $username"
        phoneText.text = "Phone: $phone"
    }
}
