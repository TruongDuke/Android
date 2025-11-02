package com.example.linearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtDisplay: TextView

    private var currentEntry = "0"
    private var accumulator: Long? = null
    private var pendingOp: Char? = null
    private var expectingNewEntry = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtDisplay = findViewById(R.id.txtDisplay)

        // Map các nút số
        val digitIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val d = (it as Button).text.toString()
                onDigit(d)
            }
        }

        // Toán tử
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperator('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperator('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperator('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperator('/') }

        // Điều khiển khác
        findViewById<Button>(R.id.btnEq).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btnC).setOnClickListener { onClearAll() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { onClearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { onBackspace() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { onToggleSign() }
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            // Bỏ qua vì chỉ xử lý số nguyên
        }
    }

    private fun onDigit(d: String) {
        if (expectingNewEntry) {
            currentEntry = d
            expectingNewEntry = false
        } else {
            currentEntry = if (currentEntry == "0") d else currentEntry + d
        }
        render()
    }

    private fun onOperator(op: Char) {
        if (accumulator == null) {
            accumulator = currentEntry.toLong()
        } else if (!expectingNewEntry) {
            accumulator = applyOp(accumulator!!, currentEntry.toLong(), pendingOp)
        }
        pendingOp = op
        expectingNewEntry = true
        render(accumulator)
    }

    private fun onEquals() {
        if (pendingOp != null && accumulator != null) {
            val right = currentEntry.toLong()
            val result = applyOp(accumulator!!, right, pendingOp)
            accumulator = null
            pendingOp = null
            currentEntry = result.toString()
            expectingNewEntry = true
            render()
        }
    }

    private fun onClearAll() { // C
        currentEntry = "0"
        accumulator = null
        pendingOp = null
        expectingNewEntry = true
        render()
    }

    private fun onClearEntry() { // CE
        currentEntry = "0"
        expectingNewEntry = true
        render()
    }

    private fun onBackspace() { // BS
        if (expectingNewEntry) return
        currentEntry = if (currentEntry.length <= 1 || (currentEntry.length == 2 && currentEntry.startsWith("-"))) {
            "0"
        } else {
            currentEntry.dropLast(1)
        }
        render()
    }

    private fun onToggleSign() {
        if (currentEntry == "0") return
        currentEntry = if (currentEntry.startsWith("-")) currentEntry.drop(1) else "-$currentEntry"
        render()
    }

    private fun applyOp(a: Long, b: Long, op: Char?): Long {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0L) 0 else a / b
            else -> b
        }
    }

    private fun render(value: Long? = null) {
        txtDisplay.text = (value?.toString() ?: currentEntry)
    }
}