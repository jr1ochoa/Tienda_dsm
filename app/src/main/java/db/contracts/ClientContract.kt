package db.contracts

import android.provider.BaseColumns

object ClientContract {
    object ClientEntry : BaseColumns {
        const val TABLE_NAME = "client"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD = "password"
    }
}
