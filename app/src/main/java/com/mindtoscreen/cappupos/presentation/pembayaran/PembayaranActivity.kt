package com.mindtoscreen.cappupos.presentation.pembayaran

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityPembayaranBinding
import com.mindtoscreen.cappupos.presentation.transaksi.BelumBayarActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

/**
 * Layar pembayaran transaksi. FR-06.
 * Tunai: nominal manual/suggestion (uang pas), kembalian otomatis.
 * Non-tunai: pilih metode, nominal = subtotal (tanpa kembalian).
 */
@AndroidEntryPoint
class PembayaranActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }

    private lateinit var binding: ActivityPembayaranBinding
    private val viewModel: PembayaranViewModel by viewModels()

    private val hargaFormat: NumberFormat = NumberFormat
        .getNumberInstance(Locale("in", "ID")).apply {
            maximumFractionDigits = 0
            minimumFractionDigits = 0
        }

    private var subtotal: Double = 0.0
    private var selectedMetode: String = "tunai"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupMetode()
        setupNominalWatcher()
        setupBayar()

        val orderId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
        viewModel.loadOrder(orderId)
        observeState(orderId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.pembayaran_title)
    }

    private fun setupMetode() {
        binding.radioTunai.setOnClickListener {
            selectMetode("tunai", binding.radioTunai, binding.radioQris, binding.radioDebit, binding.radioTransfer)
        }
        binding.radioQris.setOnClickListener {
            selectMetode("qris", binding.radioQris, binding.radioTunai, binding.radioDebit, binding.radioTransfer)
        }
        binding.radioDebit.setOnClickListener {
            selectMetode("debit", binding.radioDebit, binding.radioTunai, binding.radioQris, binding.radioTransfer)
        }
        binding.radioTransfer.setOnClickListener {
            selectMetode("transfer", binding.radioTransfer, binding.radioTunai, binding.radioQris, binding.radioDebit)
        }
    }

    private fun selectMetode(metode: String, aktif: RadioButton, vararg lainnya: RadioButton) {
        selectedMetode = metode
        aktif.isChecked = true
        lainnya.forEach { it.isChecked = false }
        val nonTunai = metode != "tunai"
        binding.inputNominalDiterima.isVisible = !nonTunai
        binding.textSuggestion.isVisible = !nonTunai
        binding.labelKembalian.isVisible = !nonTunai
        if (nonTunai) {
            binding.inputNominalDiterima.setText("")
            binding.textKembalian.text = hargaFormat.format(0)
        } else {
            // Saran default: uang pas
            binding.inputNominalDiterima.setText(hargaFormat.format(subtotal).replace(".", ""))
        }
    }

    private fun setupNominalWatcher() {
        binding.inputNominalDiterima.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateKembalian()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.textSuggestion.setOnClickListener {
            binding.inputNominalDiterima.setText(hargaFormat.format(subtotal).replace(".", ""))
            updateKembalian()
        }
    }

    private fun updateKembalian() {
        val raw = binding.inputNominalDiterima.text.toString()
        val nominal = raw.toDoubleOrNull() ?: 0.0
        val kembalian = (nominal - subtotal).coerceAtLeast(0.0)
        binding.textKembalian.text = hargaFormat.format(kembalian)
    }

    private fun setupBayar() {
        binding.btnBayar.setOnClickListener {
            val raw = binding.inputNominalDiterima.text.toString()
            val nominal = if (selectedMetode == "tunai") {
                raw.toDoubleOrNull() ?: 0.0
            } else {
                subtotal
            }
            viewModel.bayar(getOrderId(), selectedMetode, nominal)
        }
    }

    private fun getOrderId(): String = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()

    private fun observeState(orderId: String) {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.loading
                binding.contentPanel.isVisible = !state.loading

                state.order?.let { order ->
                    subtotal = order.subtotal
                    binding.textSubtotal.text = "Rp ${hargaFormat.format(order.subtotal)}"
                    // Refresh suggestion untuk metode tunai default
                    if (selectedMetode == "tunai" && binding.inputNominalDiterima.text.isNullOrBlank()) {
                        binding.inputNominalDiterima.setText(hargaFormat.format(order.subtotal).replace(".", ""))
                    }
                }

                if (state.paid) {
                    Toast.makeText(this@PembayaranActivity, getString(R.string.msg_bayar_sukses), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PembayaranActivity, BelumBayarActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }

                state.error?.let {
                    Toast.makeText(this@PembayaranActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.clearError()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
