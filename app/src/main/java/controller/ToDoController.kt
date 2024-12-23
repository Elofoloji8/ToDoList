package com.todo.controller

import com.todo.model.ToDoItem
import com.google.firebase.database.*

class ToDoController {

    private val database = FirebaseDatabase.getInstance().getReference("todos")

    fun addToDoItem(title: String, onComplete: () -> Unit) {
        val id = database.push().key ?: return
        val todo = ToDoItem(id, title, false)
        database.child(id).setValue(todo).addOnCompleteListener { onComplete() }
    }

    fun deleteToDoItem(id: String, onComplete: () -> Unit) {
        database.child(id).removeValue().addOnCompleteListener { onComplete() }
    }

    fun fetchToDoList(onDataChange: (List<ToDoItem>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList = mutableListOf<ToDoItem>()
                for (item in snapshot.children) {
                    val todo = item.getValue(ToDoItem::class.java)
                    if (todo != null) todoList.add(todo)
                }
                onDataChange(todoList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}