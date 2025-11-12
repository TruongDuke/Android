package com.example.simplelist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val radioEven = findViewById<RadioButton>(R.id.radioEven)
        val radioOdd = findViewById<RadioButton>(R.id.radioOdd)
        val radioSquare = findViewById<RadioButton>(R.id.radioSquare)
        val radioPrime = findViewById<RadioButton>(R.id.radioPrime)
        val radioCube = findViewById<RadioButton>(R.id.radioCube)
        val radioFibo = findViewById<RadioButton>(R.id.radioFibo)
        val radioGroupTop = findViewById<RadioGroup>(R.id.radioGroupTop)
        val radioGroupMore = findViewById<RadioGroup>(R.id.radioGroupMore)
        val listViewResult = findViewById<ListView>(R.id.listViewResult)
        val textViewError = findViewById<TextView>(R.id.textViewError)

        fun updateList() {
            val inputText = editTextNumber.text.toString()
            val n = inputText.toIntOrNull()

            if (n == null || n < 0) {
                listViewResult.visibility = View.GONE
                textViewError.text = "Không có số nào thỏa mãn"
                textViewError.visibility = TextView.VISIBLE
                return
            }

            val resultList = mutableListOf<Int>()
            when {
                radioEven.isChecked -> {
                    var i = 0
                    while (i < n) {
                        if (i % 2 == 0) resultList.add(i)
                        i++
                    }
                }
                radioOdd.isChecked -> {
                    var i = 0
                    while (i < n) {
                        if (i % 2 != 0) resultList.add(i)
                        i++
                    }
                }
                radioSquare.isChecked -> {
                    var i = 0
                    while (i * i < n) {
                        resultList.add(i * i)
                        i++
                    }
                }
                radioPrime.isChecked -> {
                    var i = 2
                    while (i < n) {
                        if (isPrime(i)) resultList.add(i)
                        i++
                    }
                }
                radioCube.isChecked -> {
					var i = 1
					while (i < n) {
						if (isPerfect(i)) resultList.add(i)
						i++
					}
                }
                radioFibo.isChecked -> {
                    var a = 0
                    var b = 1
                    while (a < n) {
                        resultList.add(a)
                        val next = a + b
                        a = b
                        b = next
                    }
                }
                else -> {
                    // Không chọn gì
                }
            }

            if (resultList.isEmpty()) {
                listViewResult.visibility = View.GONE
                textViewError.text = "Không có số nào thỏa mãn"
                textViewError.visibility = TextView.VISIBLE
            } else {
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
                listViewResult.adapter = adapter
                textViewError.visibility = View.GONE
                listViewResult.visibility = View.VISIBLE
            }
        }

        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Tạo listener cho hàng nút trên
        var topListener: RadioGroup.OnCheckedChangeListener? = null
        var moreListener: RadioGroup.OnCheckedChangeListener? = null
        
        topListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                // Clear hàng nút dưới khi chọn hàng nút trên
                radioGroupMore.setOnCheckedChangeListener(null)
                radioGroupMore.clearCheck()
                radioGroupMore.setOnCheckedChangeListener(moreListener)
                updateList()
            }
        }
        
        // Tạo listener cho hàng nút dưới
        moreListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                // Clear hàng nút trên khi chọn hàng nút dưới
                radioGroupTop.setOnCheckedChangeListener(null)
                radioGroupTop.clearCheck()
                radioGroupTop.setOnCheckedChangeListener(topListener)
                updateList()
            }
        }
        
        // Gắn listener ban đầu
        radioGroupTop.setOnCheckedChangeListener(topListener)
        radioGroupMore.setOnCheckedChangeListener(moreListener)

        // Khởi tạo cập nhật lần đầu
        updateList()
    }

    private fun isPrime(x: Int): Boolean {
        if (x < 2) return false
        if (x == 2 || x == 3) return true
        if (x % 2 == 0) return false
        var i = 3
        while (i * i <= x) {
            if (x % i == 0) return false
            i += 2
        }
        return true
    }

	private fun isPerfect(x: Int): Boolean {
		if (x < 2) return false
		var sum = 1
		var i = 2
		while (i * i <= x) {
			if (x % i == 0) {
				sum += i
				val other = x / i
				if (other != i) {
					sum += other
				}
			}
			i++
		}
		return sum == x
	}
}