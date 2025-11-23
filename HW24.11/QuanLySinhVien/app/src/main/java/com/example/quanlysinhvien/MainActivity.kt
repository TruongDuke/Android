package com.example.quanlysinhvien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var rcvStudent: RecyclerView

    private val listStudent = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter
    private var selectedPos = -1    // vị trí đang chọn để Update

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapping()
        initList()
        setEvents()
    }

    private fun mapping() {
        edtId = findViewById(R.id.edtId)
        edtName = findViewById(R.id.edtName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        rcvStudent = findViewById(R.id.rcvStudent)
    }

    private fun initList() {
        // Thêm vài sinh viên mẫu giống hình
        listStudent.add(Student("20200001", "Nguyễn Văn A"))
        listStudent.add(Student("20200002", "Trần Thị B"))
        listStudent.add(Student("20200003", "Lê Văn C"))
        listStudent.add(Student("20200004", "Phạm Thị D"))
        listStudent.add(Student("20200005", "Hoàng Văn E"))

        adapter = StudentAdapter(listStudent,
            object : StudentAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    selectedPos = position
                    val s = listStudent[position]
                    edtId.setText(s.id)      // gọi getter Java như property
                    edtName.setText(s.name)
                }

                override fun onDeleteClick(position: Int) {
                    listStudent.removeAt(position)
                    adapter.notifyItemRemoved(position)

                    if (position == selectedPos) {
                        selectedPos = -1
                        edtId.setText("")
                        edtName.setText("")
                    }
                }
            })

        rcvStudent.layoutManager = LinearLayoutManager(this)
        rcvStudent.adapter = adapter
    }

    private fun setEvents() {
        // Nút Add
        btnAdd.setOnClickListener {
            val id = edtId.text.toString().trim()
            val name = edtName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "Nhập đầy đủ MSSV và Họ tên",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            listStudent.add(Student(id, name))
            adapter.notifyItemInserted(listStudent.size - 1)

            edtId.setText("")
            edtName.setText("")
            selectedPos = -1
        }

        // Nút Update
        btnUpdate.setOnClickListener {
            if (selectedPos == -1) {
                Toast.makeText(
                    this@MainActivity,
                    "Chọn sinh viên trong danh sách để sửa",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val id = edtId.text.toString().trim()
            val name = edtName.text.toString().trim()

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "Không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val s = listStudent[selectedPos]
            s.id = id      // dùng setter của Java
            s.name = name
            adapter.notifyItemChanged(selectedPos)
        }
    }
}