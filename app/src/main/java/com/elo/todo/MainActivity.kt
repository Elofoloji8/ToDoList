package com.elo.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.todo.controller.ToDoController
import com.todo.ui.ToDoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = ToDoController()
            ToDoScreen(controller = controller)
        }
    }
}