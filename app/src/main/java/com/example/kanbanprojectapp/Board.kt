package com.example.kanbanprojectapp

data class Board(
    val id: Int,
    var name: String,
    var color: Int,
    var tasks: MutableList<Task>
)

data class Task(
    var name: String,
    var description: String,
    var date: String? = null,
    var status: String
)