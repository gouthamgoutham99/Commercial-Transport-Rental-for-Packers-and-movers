package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)
        val loginRedirectButton = findViewById<Button>(R.id.loginRedirectButton)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters long, contain an uppercase letter, a number, and a special character.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val url = "http://10.0.2.2:8080/truck_rental/signup.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                { error ->
                    Toast.makeText(this, "Signup Failed: ${error.message}", Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    return mapOf(
                        "username" to username,
                        "email" to email,
                        "phone" to phone,
                        "password" to password
                    )
                }
            }

            Volley.newRequestQueue(this).add(stringRequest)
        }

        loginRedirectButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val lengthCheck = password.length >= 6
        val upperCaseCheck = password.any { it.isUpperCase() }
        val digitCheck = password.any { it.isDigit() }
        val specialCharCheck = password.any { !it.isLetterOrDigit() }

        return lengthCheck && upperCaseCheck && digitCheck && specialCharCheck
    }
}
