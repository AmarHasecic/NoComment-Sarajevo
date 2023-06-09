package climbing.ba.nocomment.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.database.addMemberToDatabase
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.Payment
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMemberScreen(navController: NavController) {

    val fullName = remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dodaj člana")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            label = { Text(text = "Ime i prezime") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle form submission
                val name = fullName.value

                if (name.isNotEmpty()) {

                    var payments = listOf(Payment(0,Month.JANUARY),
                        Payment( 0,Month.FEBRUARY),
                        Payment( 0,Month.MARCH),
                        Payment(0,Month.APRIL),
                        Payment( 0,Month.MAY),
                        Payment( 0,Month.JUNE),
                        Payment( 0,Month.JULY),
                        Payment( 0,Month.AUGUST),
                        Payment( 0,Month.SEPTEMBER),
                        Payment(  0,Month.OCTOBER),
                        Payment(0,  Month.NOVEMBER),
                        Payment(0,  Month.DECEMBER)
                    )
                    var member: Member = Member(name,payments)

                    addMemberToDatabase(member, context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0EA570),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Dodaj",
                color = Color.White
            )
        }
    }
}
