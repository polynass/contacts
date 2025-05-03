package ru.kuzichevapd.contacts.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kuzichevapd.contacts.viewmodel.ContactsViewModel
import androidx.core.net.toUri

@Composable
fun ContactsScreen(viewModel: ContactsViewModel) {
    val context = LocalContext.current
    val contacts by viewModel.contacts.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadContacts()
    }

    if (contacts.isEmpty()) {
        Text(
            text = "Список контактов пуст",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            contacts
                .groupBy { it.name.firstOrNull()?.uppercaseChar() ?: '#' }
                .toSortedMap()
                .forEach { (initial, groupedContacts) ->
                    item {
                        Column {
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(
                                text = initial.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    items(groupedContacts) { contact ->
                        Column {
                            ContactItem(contact) {
                                contact.phoneNumber?.let {
                                    val intent = Intent(Intent.ACTION_CALL).apply {
                                        data = "tel:$it".toUri()
                                    }
                                    context.startActivity(intent)
                                }
                            }
                            // Разделитель после контакта
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 72.dp)
                            )
                        }
                    }
                }
        }
    }
}