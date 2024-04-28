package com.example.Tienda.adapters

import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualstore.R
import com.example.Tienda.models.Product
import db.contracts.CartItemContract
import db.helpers.DatabaseHelper

class ProductAdapter(private val productList: List<Product>, private val context: Context, private val userId: Int) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.activity_item, parent, false)) {
        private var productNameView: TextView? = null
        private var productPriceView: TextView? = null
        private var addToCartButton: Button? = null

        init {
            productNameView = itemView.findViewById(R.id.productName)
            productPriceView = itemView.findViewById(R.id.productPrice)
            addToCartButton = itemView.findViewById(R.id.addToCartButton)
        }

        fun bind(product: Product, onAddToCartClicked: (Product) -> Unit) {
            productNameView?.text = product.name
            productPriceView?.text = "$${product.price}"
            addToCartButton?.setOnClickListener { onAddToCartClicked(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = productList[position]
        holder.bind(product) {
            addToCart(it)
        }
    }

    private fun addToCart(product: Product) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(CartItemContract.CartItemEntry.COLUMN_NAME_CLIENT_ID, userId)
            put(CartItemContract.CartItemEntry.COLUMN_NAME_PRODUCT_ID, product.id)
            put(CartItemContract.CartItemEntry.COLUMN_NAME_QUANTITY, 1)
        }

        db?.insert(CartItemContract.CartItemEntry.TABLE_NAME, null, values)
        dbHelper.close()

        Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int = productList.size
}
