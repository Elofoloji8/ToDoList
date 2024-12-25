package com.todo.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.todo.controller.ToDoController
import com.todo.model.ToDoItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete // Import Delete icon
import com.todo.utils.formatTimestamp

@OptIn(ExperimentalMaterial3Api::class) // Eğer deneysel API kullanıyorsanız, uyarı için bu satırı ekleyebilirsiniz.
@Composable
fun ToDoScreen(controller: ToDoController) {
    val context = LocalContext.current
    val (title, setTitle) = remember { mutableStateOf("") }
    val (todoList, setTodoList) = remember { mutableStateOf(listOf<ToDoItem>()) }

    // Firebase'den verileri çek
    LaunchedEffect(Unit) {
        controller.fetchToDoList { fetchedList ->
            setTodoList(fetchedList)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Başlık Alanı
        Text(
            text = "To-Do Listesi",
            style = MaterialTheme.typography.headlineMedium, // h5 yerine
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Yeni To-Do ekleme alanı
        OutlinedTextField(
            value = title,
            onValueChange = setTitle,
            label = { Text("Yeni To-Do Girin") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyLarge // textStyle ile renk ve stil ayarlanabilir
        )

        // Ekle Butonu
        Button(
            onClick = {
                if (title.isNotEmpty()) {
                    controller.addToDoItem(title) {
                        Toast.makeText(context, "To-Do eklendi!", Toast.LENGTH_SHORT).show()
                    }
                    setTitle("") // TextField'ı temizle
                } else {
                    Toast.makeText(context, "Lütfen bir başlık girin!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Ekle", style = MaterialTheme.typography.titleMedium) // titleMedium ya da uygun stil kullanılabilir
        }

        // To-Do Listesi
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todoList) { todo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Column( // Row yerine Column kullanıyoruz
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Başlık (Notun kendisi)
                        Text(
                            text = todo.title,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Tarih Gösterimi
                        Text(
                            text = "Oluşturulma Tarihi: ${formatTimestamp(todo.timestamp)}", // formatTimestamp fonksiyonu çağırılıyor
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    IconButton(
                        onClick = {
                            controller.deleteToDoItem(todo.id) {
                                Toast.makeText(context, "To-Do silindi!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Sil",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

