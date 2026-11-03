package com.example.androidimageoptimization.data

import android.content.Context
import com.example.androidimageoptimization.model.Product
import org.json.JSONArray

class ProductRepository(context: Context) {
    private val appContext: Context = context.applicationContext
    private var cachedList: List<Product>? = null

    private fun loadAll(): List<Product> {
        cachedList?.let { return it }
        val jsonText = appContext.assets.open("products.json").bufferedReader().use { it.readText() }
        val array = JSONArray(jsonText)
        val list = ArrayList<Product>(array.length())

        for (i in 0 until array.length()) {
            val o = array.getJSONObject(i)
            list.add(
                Product(
                    o.getInt("id"),
                    o.getString("name"),
                    o.getString("description"),
                    o.getString("thumbUrl"),
                    o.getString("imageUrl"),
                )
            )
        }
        cachedList = list
        return list
    }

    fun getProducts(count: Int): List<Product> = loadAll().take(count)

    fun getProductById(id: Int): Product? = loadAll().firstOrNull { it.id == id }
}
