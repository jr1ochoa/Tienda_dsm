package db.contracts

import android.provider.BaseColumns

object ProductContract {
    object ProductEntry : BaseColumns {
        const val TABLE_NAME = "product"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PRICE = "price"
    }
}
