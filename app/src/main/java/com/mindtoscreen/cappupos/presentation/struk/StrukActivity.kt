package com.mindtoscreen.cappupos.presentation.struk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityStrukBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Struk digital plain-text (FR-11): item, subtotal, metode bayar, kembalian.
 * Share via Intent.ACTION_SEND text/plain — cetak thermal di luar scope
 * (DECISIONS.md [2026-09-14] poin 6).
 */
@AndroidEntryPoint
class StrukActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ORDER_ID = "orderId"

        fun createIntent(context: Context, orderId: String): Intent {
            return Intent(context, StrukActivity::class.java).apply {
                putExtra(EXTRA_ORDER_ID, orderId)
            }
        }
    }

    private lateinit var binding: ActivityStrukBinding
    private val viewModel: StrukViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupShare()
        observeStruk()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupShare() {
        binding.btnShare.setOnClickListener {
            val text = binding.tvStruk.text.toString()
            if (text.isNotBlank()) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.btn_bagikan_struk)))
            }
        }
    }

    private fun observeStruk() {
        lifecycleScope.launch {
            viewModel.strukText.collect { text ->
                text?.let { binding.tvStruk.text = it }
            }
        }
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let { binding.tvStruk.text = getString(R.string.msg_gagal_muat_struk) }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
