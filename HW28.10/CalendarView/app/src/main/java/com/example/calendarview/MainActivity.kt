package com.example.calendarview

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etBirthday: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var calendarView: CalendarView
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnRegister: Button

    // Lưu nền ban đầu của các input để khôi phục khi hợp lệ
    private val originalBg = HashMap<Int, Any?>()
    private val lightRed = Color.parseColor("#FFCDD2") // màu đỏ nhạt để đánh dấu lỗi
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        cacheOriginalBackgrounds()

        // Bấm Select → ẩn/hiện CalendarView
        btnSelectDate.setOnClickListener {
            calendarView.visibility =
                if (calendarView.visibility == android.view.View.GONE)
                    android.view.View.VISIBLE else android.view.View.GONE
        }

        // Khi chọn ngày trên CalendarView → hiển thị vào ô Birthday và ẩn lại
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            etBirthday.setText(fmt.format(cal.time))
            calendarView.visibility = android.view.View.GONE
            clearError(etBirthday)
        }

        // Bấm Register → kiểm tra hợp lệ
        btnRegister.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun bindViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        rgGender = findViewById(R.id.rgGender)
        etBirthday = findViewById(R.id.etBirthday)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        calendarView = findViewById(R.id.calendarView)
        etAddress = findViewById(R.id.etAddress)
        etEmail = findViewById(R.id.etEmail)
        cbTerms = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun cacheOriginalBackgrounds() {
        listOf(
            etFirstName, etLastName, etBirthday, etAddress, etEmail
        ).forEach { v -> originalBg[v.id] = v.background }
    }

    private fun markError(view: android.view.View) {
        when (view) {
            is EditText -> view.setBackgroundColor(lightRed)
        }
    }

    private fun clearError(view: android.view.View) {
        val bg = originalBg[view.id]
        if (bg != null && view is EditText) view.background = bg as android.graphics.drawable.Drawable
    }

    private fun validateAndSubmit() {
        var valid = true

        fun checkEmpty(et: EditText): Boolean {
            val ok = et.text.toString().trim().isNotEmpty()
            if (!ok) markError(et) else clearError(et)
            return ok
        }

        val v1 = checkEmpty(etFirstName)
        val v2 = checkEmpty(etLastName)
        val v3 = (rgGender.checkedRadioButtonId != -1)
        if (!v3) Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()

        val v4 = checkEmpty(etBirthday)
        val v5 = checkEmpty(etAddress)
        val v6 = checkEmpty(etEmail)
        val v7 = cbTerms.isChecked

        if (!v7)
            Toast.makeText(this, "You must agree to Terms of Use", Toast.LENGTH_SHORT).show()

        valid = v1 && v2 && v3 && v4 && v5 && v6 && v7

        if (valid) {
            Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
        }
    }
}