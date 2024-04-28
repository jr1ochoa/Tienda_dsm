package com.example.Tienda

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.virtualstore.R
import db.repositories.ClientRepository

class RegisterActivity : Activity() {

    private lateinit var clientRepository: ClientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        clientRepository = ClientRepository(this)

        val editTextName = findViewById<EditText>(R.id.editTextNameRegister)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmailRegister)
        val editTextPassword = findViewById<EditText>(R.id.editTextPasswordRegister)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val result = clientRepository.register(name, email, password)
                if (result) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar, intente de nuevo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
