package com.example.todo

import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val context: Context, private val list: ArrayList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val dbHandler = DBHandler(context)
    private lateinit var dialog: Dialog
    private lateinit var etUpdateTask: EditText
    private lateinit var btnUpdateTask: Button
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val tvTaskName: TextView = view.findViewById(R.id.tvTaskName)
        val tvTaskTime: TextView = view.findViewById(R.id.tvTaskTime)
        val ivEdit: ImageView = view.findViewById(R.id.ivEdit)
        val ivDelete: ImageView = view.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_each_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]
        holder.tvTaskName.text = task.taskName
        holder.tvTaskTime.text = task.taskTime


        dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_update_task)
        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        etUpdateTask = dialog.findViewById(R.id.etUpdateTask)
        btnUpdateTask = dialog.findViewById(R.id.btnUpdateTask)

        holder.ivEdit.setOnClickListener {
            etUpdateTask.setText(task.taskName)
            dialog.show()
        }
        btnUpdateTask.setOnClickListener {
            val taskName = etUpdateTask.text.toString()
            dbHandler.updateTask(Task(task.id, taskName, task.taskTime, task.isComplete))
            notifyItemChanged(holder.adapterPosition)
            dialog.dismiss()
        }

        holder.ivDelete.setOnClickListener {
            dbHandler.deleteTask(task.id)
            list.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }
}