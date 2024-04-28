package db.seeders

import android.content.ContentValues
import android.content.Context
import db.contracts.ProductContract
import db.helpers.DatabaseHelper

class ProductDataSeeder(private val context: Context) {

    private val preferences = context.getSharedPreferences("seed_preferences", Context.MODE_PRIVATE)

    fun seed() {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase

        val alreadySeeded = preferences.getBoolean("product_data_already_seeded", false)

        if (!alreadySeeded) {

            val products = listOf(
                ContentValues().apply {
                    put(ProductContract.ProductEntry.COLUMN_NAME_NAME, "15 Pro Max")
                    put(ProductContract.ProductEntry.COLUMN_NAME_PRICE, 1299.99)
                },
                ContentValues().apply {
                    put(ProductContract.ProductEntry.COLUMN_NAME_NAME, "S24 ultra")
                    put(ProductContract.ProductEntry.COLUMN_NAME_PRICE, 1699.99)
                },
                ContentValues().apply {
                    put(ProductContract.ProductEntry.COLUMN_NAME_NAME, "PIXEL 9 PRO")
                    put(ProductContract.ProductEntry.COLUMN_NAME_PRICE, 899.00)
                },

                )

            db.beginTransaction()
            try {
                for (product in products) {
                    db.insert(ProductContract.ProductEntry.TABLE_NAME, null, product)
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }

            dbHelper.close()

            preferences.edit().putBoolean("product_data_already_seeded", true).apply()
        }
    }
}
