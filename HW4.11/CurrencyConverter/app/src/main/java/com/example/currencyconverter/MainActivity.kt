package com.example.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var amountFrom: EditText
    private lateinit var amountTo: EditText
    private lateinit var currencyFrom: Spinner
    private lateinit var currencyTo: Spinner
    private lateinit var rates: Map<String, Double>
    private var isUpdatingText: Boolean = false
    private var lastEdited: EditSource = EditSource.FROM

    private enum class EditSource { FROM, TO }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        amountFrom = findViewById(R.id.amountFrom)
        amountTo = findViewById(R.id.amountTo)
        currencyFrom = findViewById(R.id.currencyFrom)
        currencyTo = findViewById(R.id.currencyTo)

        setupFixedRatesAndUi()

        addListeners()
    }

    private fun setupFixedRatesAndUi() {
        // Fixed rates relative to USD (ví dụ minh hoạ, không dùng cho giao dịch thật)
        rates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.92,
            "JPY" to 151.0,
            "GBP" to 0.79,
            "AUD" to 1.53,
            "CAD" to 1.38,
            "CNY" to 7.25,
            "KRW" to 1370.0,
            "VND" to 24500.0,
            "INR" to 83.2,
            "SGD" to 1.36
        )

        val currencyList = rates.keys.toList().sorted()
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyFrom.adapter = adapter
        currencyTo.adapter = adapter

        // Mặc định: From = USD, To = EUR (nếu có)
        val fromIndex = currencyList.indexOf("USD").takeIf { it >= 0 } ?: 0
        val toIndex = currencyList.indexOf("EUR").takeIf { it >= 0 } ?: (if (currencyList.size > 1) 1 else 0)
        currencyFrom.setSelection(fromIndex)
        currencyTo.setSelection(toIndex)
    }

    private fun addListeners() {
        amountFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUpdatingText) return
                lastEdited = EditSource.FROM
                val text = s?.toString() ?: ""
                if (text.isBlank()) {
                    safeSetText(amountTo, "")
                    return
                }
                val value = text.toDoubleOrNull() ?: return
                val result = convert(value, currencyFrom.selectedItem.toString(), currencyTo.selectedItem.toString())
                safeSetText(amountTo, formatAmount(result))
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        amountTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUpdatingText) return
                lastEdited = EditSource.TO
                val text = s?.toString() ?: ""
                if (text.isBlank()) {
                    safeSetText(amountFrom, "")
                    return
                }
                val value = text.toDoubleOrNull() ?: return
                val result = convert(value, currencyTo.selectedItem.toString(), currencyFrom.selectedItem.toString())
                safeSetText(amountFrom, formatAmount(result))
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (lastEdited) {
                    EditSource.FROM -> {
                        val value = amountFrom.text.toString().toDoubleOrNull() ?: run {
                            safeSetText(amountTo, "")
                            return
                        }
                        val result = convert(value, currencyFrom.selectedItem.toString(), currencyTo.selectedItem.toString())
                        safeSetText(amountTo, formatAmount(result))
                    }
                    EditSource.TO -> {
                        val value = amountTo.text.toString().toDoubleOrNull() ?: run {
                            safeSetText(amountFrom, "")
                            return
                        }
                        val result = convert(value, currencyTo.selectedItem.toString(), currencyFrom.selectedItem.toString())
                        safeSetText(amountFrom, formatAmount(result))
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        currencyFrom.onItemSelectedListener = spinnerListener
        currencyTo.onItemSelectedListener = spinnerListener
    }

    private fun convert(amount: Double, from: String, to: String): Double {
        val fromRate = rates[from] ?: return 0.0
        val toRate = rates[to] ?: return 0.0
        return amount * (toRate / fromRate)
    }

    private fun safeSetText(target: EditText, text: String) {
        isUpdatingText = true
        target.setText(text)
        isUpdatingText = false
    }

    private fun formatAmount(value: Double): String {
        return String.format("%.2f", value)
    }
}