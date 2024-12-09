package climbing.ba.nocomment.reusables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import climbing.ba.nocomment.model.Member

@Composable
fun ShowDialog(
    showDialog: MutableState<Boolean>,
    dialogTitle: String,
    dialogMessage: String,
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = dialogTitle,
                color = Color.Black
            ) },
            text = { Text(text = dialogMessage,
                color = Color.Black) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text(text = cancelButtonText)
                }
            },
            backgroundColor = Color.White
        )
    }
}
