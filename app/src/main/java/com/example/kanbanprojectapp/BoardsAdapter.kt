package com.example.kanbanprojectapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class BoardsAdapter(
    private val context: Context,
    private val boards: MutableList<Board>
) : RecyclerView.Adapter<BoardsAdapter.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.board, parent, false)
        return BoardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val currentBoard = boards[position]
        holder.boardName.text = currentBoard.name
        holder.cardView.setCardBackgroundColor(currentBoard.color)

        // Handle click event on CardView
        holder.cardView.setOnClickListener {
            showDialog(currentBoard, position)
            if (holder.tasksContainer.visibility == View.GONE) {
                // Populate tasksContainer with the tasks views
                // For example, for each task, create a TextView and add it to tasksContainer

                holder.tasksContainer.visibility = View.VISIBLE
            } else {
                holder.tasksContainer.visibility = View.GONE
            }
        }
//        holder.newTaskButton.setOnClickListener {
//            // Show a dialog for the user to enter task details
//            val taskDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_new_task, null)
//
//            val taskDialog = AlertDialog.Builder(context)
//                .setView(taskDialogView)
//                .setTitle("New Task")
//                .setPositiveButton("Add") { _, _ ->
//                    val taskName = taskDialogView.findViewById<EditText>(R.id.taskNameEditText).text.toString()
//                    val taskDescription = taskDialogView.findViewById<EditText>(R.id.taskDescriptionEditText).text.toString()
//
//                    // Add the new task to the current board and update the UI
//                    currentBoard.tasks.add(Task(taskName, taskDescription, "Tommorow", "Undone"))
//                    notifyItemChanged(position)
//                }
//                .setNegativeButton("Cancel", null)
//                .create()
//
//            taskDialog.show()
//        }
        holder.deleteBoardButton.setOnClickListener {
            boards.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.cardView.setOnLongClickListener {
            boards.removeAt(position)
            notifyItemRemoved(position)
            true
        }
    }

    fun updateBoards(newBoards: List<Board>) {
        this.boards.clear()
        this.boards.addAll(newBoards)
        notifyDataSetChanged()
    }

    private fun showDialog(board: Board, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Board Details")
        builder.setMessage("Name: ${board.name}\nColor: ${board.color}")
        builder.setPositiveButton("Delete") { dialog, _ ->
            boards.removeAt(position)
            notifyItemRemoved(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun getItemCount(): Int {
        return boards.size
    }

    class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boardName: TextView = itemView.findViewById(R.id.boardName)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val tasksContainer: LinearLayout = itemView.findViewById(R.id.tasksContainer)
        val deleteBoardButton: ImageButton = itemView.findViewById(R.id.deleteBoardButton)
        val newTaskButton: ImageButton = itemView.findViewById(R.id.newTaskButton)
    }
}