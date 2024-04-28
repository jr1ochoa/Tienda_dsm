package com.example.Tienda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualstore.R
import com.example.Tienda.adapters.ProductAdapter
import com.example.Tienda.models.Product
import db.contracts.ProductContract
import db.helpers.DatabaseHelper

class ProductsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("UserId", -1)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = getProductListFromDatabase()
        val adapter = ProductAdapter(products, this, userId)
        recyclerView.adapter = adapter

        val btnViewCart: Button = findViewById(R.id.btnViewCart)
        btnViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val btnLogout: Button = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun getProductListFromDatabase(): List<Product> {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            ProductContract.ProductEntry.COLUMN_NAME_ID,
            ProductContract.ProductEntry.COLUMN_NAME_NAME,
            ProductContract.ProductEntry.COLUMN_NAME_PRICE
        )

        val cursor = db.query(
            ProductContract.ProductEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val productList = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_ID))
                val name = getString(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_NAME))
                val price = getDouble(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRICE))
                productList.add(Product(id, name, price))
            }
        }
        cursor.close()
        dbHelper.close()

        return productList
    }

}
