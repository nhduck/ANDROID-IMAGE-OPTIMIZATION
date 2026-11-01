package com.example.androidimageoptimization

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidimageoptimization.data.ProductRepository
import com.example.androidimageoptimization.databinding.ActivityMainBinding
import com.example.androidimageoptimization.ui.ProductAdapter
import com.example.androidimageoptimization.ui.clearImageCache

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: ProductRepository
    private var currentCount = DEFAULT_COUNT

    private val adapter = ProductAdapter { product ->
        startActivity(DetailActivity.newIntent(this, product.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        repository = ProductRepository(this)
        currentCount = savedInstanceState?.getInt(KEY_COUNT, DEFAULT_COUNT) ?: DEFAULT_COUNT

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        setupMenu()
        showProducts(currentCount)
    }

    private fun setupMenu() {
        // Đánh dấu đúng mục đang chọn (quan trọng khi xoay màn hình)
        binding.toolbar.menu.findItem(menuIdFor(currentCount))?.isChecked = true

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_count_100 -> selectCount(item, 100)
                R.id.action_count_500 -> selectCount(item, 500)
                R.id.action_count_1000 -> selectCount(item, 1000)
                R.id.action_clear_cache -> {
                    clearImageCache(this)
                    Toast.makeText(this, R.string.msg_clear_cache_pending, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun selectCount(item: android.view.MenuItem, count: Int): Boolean {
        item.isChecked = true
        currentCount = count
        showProducts(count)
        return true
    }

    private fun showProducts(count: Int) {
        adapter.submitList(repository.getProducts(count)) {
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun menuIdFor(count: Int) = when (count) {
        500 -> R.id.action_count_500
        1000 -> R.id.action_count_1000
        else -> R.id.action_count_100
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNT, currentCount)
    }

    companion object {
        private const val DEFAULT_COUNT = 100
        private const val KEY_COUNT = "key_count"
    }
}