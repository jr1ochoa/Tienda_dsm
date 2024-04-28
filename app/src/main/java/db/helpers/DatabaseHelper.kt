package db.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import db.contracts.CartItemContract
import db.contracts.ClientContract
import db.contracts.ProductContract

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CART_ITEMS)
        db.execSQL(SQL_CREATE_CLIENTS)
        db.execSQL(SQL_CREATE_PRODUCTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_CART_ITEMS)
        db.execSQL(SQL_DELETE_CLIENTS)
        db.execSQL(SQL_DELETE_PRODUCTS)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "VirtualStore.db"

        private const val SQL_CREATE_CART_ITEMS =
            "CREATE TABLE ${CartItemContract.CartItemEntry.TABLE_NAME} (" +
                    "${CartItemContract.CartItemEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${CartItemContract.CartItemEntry.COLUMN_NAME_CLIENT_ID} INTEGER," +
                    "${CartItemContract.CartItemEntry.COLUMN_NAME_PRODUCT_ID} INTEGER," +
                    "${CartItemContract.CartItemEntry.COLUMN_NAME_QUANTITY} INTEGER)"

        private const val SQL_DELETE_CART_ITEMS =
            "DROP TABLE IF EXISTS ${CartItemContract.CartItemEntry.TABLE_NAME}"

        private const val SQL_CREATE_CLIENTS =
            "CREATE TABLE ${ClientContract.ClientEntry.TABLE_NAME} (" +
                    "${ClientContract.ClientEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${ClientContract.ClientEntry.COLUMN_NAME_NAME} TEXT," +
                    "${ClientContract.ClientEntry.COLUMN_NAME_EMAIL} TEXT," +
                    "${ClientContract.ClientEntry.COLUMN_NAME_PASSWORD} TEXT)"

        private const val SQL_DELETE_CLIENTS =
            "DROP TABLE IF EXISTS ${ClientContract.ClientEntry.TABLE_NAME}"

        private const val SQL_CREATE_PRODUCTS =
            "CREATE TABLE ${ProductContract.ProductEntry.TABLE_NAME} (" +
                    "${ProductContract.ProductEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${ProductContract.ProductEntry.COLUMN_NAME_NAME} TEXT," +
                    "${ProductContract.ProductEntry.COLUMN_NAME_PRICE} REAL)"

        private const val SQL_DELETE_PRODUCTS =
            "DROP TABLE IF EXISTS ${ProductContract.ProductEntry.TABLE_NAME}"
    }
}
