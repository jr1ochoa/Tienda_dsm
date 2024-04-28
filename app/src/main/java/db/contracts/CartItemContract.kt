package db.contracts

import android.provider.BaseColumns

object CartItemContract {
    object CartItemEntry : BaseColumns {
        const val TABLE_NAME = "cart_item"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_CLIENT_ID = "client_id"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
        const val COLUMN_NAME_QUANTITY = "quantity"
    }
}
