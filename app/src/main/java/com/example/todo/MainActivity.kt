package com.example.todo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var tvCurrentDateTime: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddNewTask: Button
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TaskAdapter
    private lateinit var dbHandler: DBHandler

    private var taskList = ArrayList<Task>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvCurrentDateTime = findViewById(R.id.tvCurrentDateTime)
        recyclerView = findViewById(R.id.recyclerView)
        btnAddNewTask = findViewById(R.id.btnAddNewTask)
        dbHandler = DBHandler(this)
        taskList = dbHandler.readTask()

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
        val formattedDateTime = currentDateTime.format(formatter)

        tvCurrentDateTime.text = formattedDateTime

        btnAddNewTask.setOnClickListener {
            val intent = Intent(applicationContext, AddTaskActivity::class.java)
            startActivity(intent)
        }
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = TaskAdapter(this, taskList)
        recyclerView.adapter = adapter

    }
}