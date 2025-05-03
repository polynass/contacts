package ru.kuzichevapd.contacts.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kuzichevapd.contacts.data.model.Contact


@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Аватар",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = contact.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            contact.phoneNumber?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}