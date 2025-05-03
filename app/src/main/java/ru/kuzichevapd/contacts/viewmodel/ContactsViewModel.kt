package ru.kuzichevapd.contacts.viewmodel

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kuzichevapd.contacts.data.model.Contact

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    fun loadContacts() {
        viewModelScope.launch {
            val contentResolver = getApplication<Application>().contentResolver
            val cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            val contactsList = mutableListOf<Contact>()

            cursor?.use {
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                while (it.moveToNext()) {
                    val name = it.getString(nameIndex) ?: ""
                    val number = it.getString(numberIndex)
                    contactsList.add(Contact(name, number))
                }
            }

            _contacts.value = contactsList
        }
    }
}
