package com.todo.model

import java.util.UUID

data class ToDoItem(
    val id: String = UUID.randomUUID().toString(), // Otomatik olarak benzersiz bir ID oluşturur
    val title: String = "",                        // Not başlığı
    val isCompleted: Boolean = false,              // Tamamlanıp tamamlanmadığını belirtir
    val timestamp: Long = System.currentTimeMillis() // Notun oluşturulma zamanı (Unix zaman damgası)
)
