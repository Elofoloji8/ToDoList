package com.todo.model // model paketinde olduğuna dikkat et!

data class ToDoItem(
    val id: String = "",
    val title: String = "",
    val isCompleted: Boolean = false
)