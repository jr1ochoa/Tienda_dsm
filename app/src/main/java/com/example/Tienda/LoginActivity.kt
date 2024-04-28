package com.example.Tienda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.virtualstore.R
import db.repositories.ClientRepository
import db.seeders.ClientDataSeeder
import db.seeders.ProductDataSeeder

class LoginActivity : Activity() {

    private lateinit var clientRepository: ClientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val clientDataSeeder = ClientDataSeeder(this)
        clientDataSeeder.seed()

        val productDataSeeder = ProductDataSeeder(this)
        productDataSeeder.seed()

        clientRepository = ClientRepository(this)

        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            val userId = clientRepository.signIn(email, password)
            if (userId != -1) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("UserId", userId)
                editor.apply()

                val intent = Intent(this, ProductsActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }


        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
