package com.mindtoscreen.cappupos.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityHomeBinding
import com.mindtoscreen.cappupos.presentation.onboarding.AddProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupFAB()
        observeProducts()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        binding.recyclerProducts.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = this@HomeActivity.adapter
        }
    }

    private fun setupFAB() {
        binding.fabTambah.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            val products = viewModel.getProducts()
            adapter.updateData(products)
            binding.swipeRefresh.isRefreshing = false
            val isEmpty = products.isEmpty()
            binding.recyclerProducts.isVisible = !isEmpty
            binding.emptyState.isVisible = isEmpty
        }
    }
}
