package com.example.androidimageoptimization

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.androidimageoptimization.data.ProductRepository
import com.example.androidimageoptimization.databinding.ActivityDetailBinding
import com.example.androidimageoptimization.ui.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, 0)
            binding.scroll.updatePadding(bottom = bars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener { finish() }

        val productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1)
        val product = ProductRepository(this).getProductById(productId)
        if (product == null) {
            finish()
            return
        }

        binding.tvName.text = product.name
        binding.tvCode.text = getString(R.string.product_code, product.id)
        binding.tvDescription.text = product.description
        binding.ivImage.loadImage(product.imageUrl)
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"

        fun newIntent(context: Context, productId: Int): Intent =
            Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_PRODUCT_ID, productId)
    }
}
