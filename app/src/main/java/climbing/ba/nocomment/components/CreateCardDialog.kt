package climbing.ba.nocomment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import climbing.ba.nocomment.R

@Composable
fun CreateCardDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String = "Kreiraj karticu",
    message: String = "Ime vlasnika kartice",
    name: String = "",
    textAccept:  String = "Kreiraj",
    textDecline: String = "Odustani"
) {
    var fullName by remember { mutableStateOf(name) }
    LaunchedEffect(name) {
        fullName = name
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.no_comment_dark_gray)
                )
            },
            text = {
                Column {
                    Text(message)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(text = "Ime i prezime") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(fullName)
                        fullName = ""
                        onDismiss()
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 5.dp),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = colorResource(id = R.color.no_comment_highlight_green),
                        contentColor = colorResource(id =R.color.no_comment_dark_gray)
                    )
                ) {
                    Text(
                        textAccept,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        fullName = ""
                        onDismiss()
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = colorResource(id = R.color.no_comment_highlight_green),
                        contentColor = colorResource(id = R.color.no_comment_dark_gray)
                    )
                ) {
                    Text(
                        textDecline,
                        modifier = Modifier.padding(3.dp)
                        )
                }
            }
        )
    }
}