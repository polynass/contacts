package ru.kuzichevapd.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kuzichevapd.contacts.ui.ContactsScreen
import ru.kuzichevapd.contacts.viewmodel.ContactsViewModel

class MainActivity : ComponentActivity() {
    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (!allGranted) {
            Toast.makeText(
                this,
                "Без разрешений приложение не может работать",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val viewModel: ContactsViewModel = viewModel()
                    val permissionsGranted = remember { checkPermissions() }

                    LaunchedEffect(permissionsGranted) {
                        if (!permissionsGranted) {
                            permissionLauncher.launch(requiredPermissions)
                        }
                    }

                    if (permissionsGranted) {
                        ContactsScreen(viewModel = viewModel)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Запрошены разрешения...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}