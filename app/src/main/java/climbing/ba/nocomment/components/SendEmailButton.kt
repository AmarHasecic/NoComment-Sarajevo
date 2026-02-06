package climbing.ba.nocomment.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import climbing.ba.nocomment.R


@Composable
fun SendEmailButton() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Potreban ti je pristupni kod?")
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Zatraži pristup",
                color = colorResource(R.color.no_comment_green),
                fontSize = 16.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { showDialog = true }
            )
        }

        if (showDialog) {
            var name by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "Zahtjev za pristup aplikaciji",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.no_comment_dark_gray)
                    )
                },
                text = {
                    Column {
                        Text("Unesite svoje ime:")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Ime i prezime") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:ahasecic1@gmail.com")
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    "NoComment App - Zahtjev za pristup"
                                )
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Zdravo, moje ime je $name i potreban mi je pristupni kod za NoComment aplikaciju."
                                )
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .padding(bottom = 8.dp, end = 5.dp),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = colorResource(id = R.color.no_comment_highlight_green),
                            contentColor = colorResource(id = R.color.no_comment_dark_gray)
                        )
                    ) {
                        Text("Pošalji", modifier = Modifier.padding(3.dp))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false },
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = colorResource(id = R.color.no_comment_highlight_green),
                            contentColor = colorResource(id = R.color.no_comment_dark_gray)
                        )
                    ) {
                        Text("Odustani", modifier = Modifier.padding(3.dp))
                    }
                }
            )
        }
    }
}

