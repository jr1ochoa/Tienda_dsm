package db.seeders

import android.content.ContentValues
import android.content.Context
import db.contracts.ClientContract
import db.helpers.DatabaseHelper

class ClientDataSeeder(private val context: Context) {

    private val preferences = context.getSharedPreferences("seed_preferences", Context.MODE_PRIVATE)

    fun seed() {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase

        val alreadySeeded = preferences.getBoolean("client_data_already_seeded", false)

        if (!alreadySeeded) {

            val clients = listOf(
                ContentValues().apply {
                    put(ClientContract.ClientEntry.COLUMN_NAME_NAME, "Adriana Soto")
                    put(ClientContract.ClientEntry.COLUMN_NAME_EMAIL, "asoto@gmail.com")
                    put(
                        ClientContract.ClientEntry.COLUMN_NAME_PASSWORD,
                        "\$2a\$10\$.qoOlpKfFwMC7pRg9/XXau/FLhFFzPWq/iKoRZbScKONnr8L4b3R2"
                    )
                },
            )

            db.beginTransaction()
            try {
                for (client in clients) {
                    db.insert(ClientContract.ClientEntry.TABLE_NAME, null, client)
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }

            dbHelper.close()

            preferences.edit().putBoolean("client_data_already_seeded", true).apply()
        }
    }
}
