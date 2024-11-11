package com.example.todo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.concurrent.thread

class DBHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "TaskDB"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val ID_COL = "id"
        private const val TASK_NAME_COL = "taskName"
        private const val TASK_TIME_COL = "taskTime"
        private const val COMPLETED_COL = "isCompleted"

    }

    fun addTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_COL, task.id)
        values.put(TASK_NAME_COL, task.taskName)
        values.put(TASK_TIME_COL, task.taskTime)
        values.put(COMPLETED_COL, task.isComplete)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun readTask(): ArrayList<Task> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val list = ArrayList<Task>()
        if (cursor.moveToFirst()) {
            do {
                list.add(Task(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list;
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_COL, task.id)
        values.put(TASK_NAME_COL, task.taskName)
        values.put(TASK_TIME_COL, task.taskTime)
        values.put(COMPLETED_COL, task.isComplete)
        db.update(TABLE_NAME, values, "id=?", arrayOf(task.id.toString()))
        db.close()
    }

    fun deleteTask(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY, $TASK_NAME_COL TEXT, $TASK_TIME_COL TEXT, $COMPLETED_COL INTEGER)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }
}