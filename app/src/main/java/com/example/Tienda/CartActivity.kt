package com.example.Tienda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualstore.R
import com.example.Tienda.adapters.CartAdapter
import com.example.Tienda.adapters.ProductAdapter
import com.example.Tienda.models.CartItem
import db.helpers.DatabaseHelper

class CartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("UserId", -1)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cartItems = getCartItems(userId)
        val adapter = CartAdapter(cartItems)
        recyclerView.adapter = adapter

        val btnBuy: Button = findViewById(R.id.btnBuy)
        btnBuy.setOnClickListener {
            val dbHelper = DatabaseHelper(this)
            val db = dbHelper.writableDatabase

            db.delete("cart_item", "client_id=?", arrayOf(userId.toString()))
            db.close()

            Toast.makeText(this, "Compra realizada con éxito!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getCartItems(userId: Int): List<CartItem> {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val query = """
            SELECT cart_item.id, client_id, product_id, quantity, product.name
            FROM cart_item
            INNER JOIN product ON cart_item.product_id = product.id
            WHERE client_id = ?
        """

        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        val items = mutableListOf<CartItem>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val clientId = getInt(getColumnIndexOrThrow("client_id"))
                val productId = getInt(getColumnIndexOrThrow("product_id"))
                val quantity = getInt(getColumnIndexOrThrow("quantity"))
                val productName = getString(getColumnIndexOrThrow("name"))
                items.add(CartItem(id, clientId, productId, quantity, productName))
            }
        }
        cursor.close()

        return items
    }

}