package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val list: ArrayList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
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
    }
}