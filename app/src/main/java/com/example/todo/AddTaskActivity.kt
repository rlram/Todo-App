package com.example.todo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTaskActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var etTaskName: EditText
    private lateinit var btnAddTask: Button
    private lateinit var dbHandler: DBHandler

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ivBack = findViewById(R.id.ivBack)
        etTaskName = findViewById(R.id.etTaskName)
        btnAddTask = findViewById(R.id.btnAddTask)
        dbHandler = DBHandler(this)

        ivBack.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        btnAddTask.setOnClickListener {
            val taskName = etTaskName.text.toString()
            if (taskName.isNotEmpty()) {
                val id = System.currentTimeMillis()
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
                val formattedDateTime = currentDateTime.format(formatter)
                dbHandler.addTask(Task(id, taskName, formattedDateTime, 0))
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}