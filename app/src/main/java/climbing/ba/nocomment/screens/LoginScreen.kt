package climbing.ba.nocomment.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.components.SendEmailButton
import climbing.ba.nocomment.database.fetchUsers
import climbing.ba.nocomment.model.User
import climbing.ba.nocomment.navigation.Screen
import climbing.ba.nocomment.sealed.DataState

@Composable
fun LoginScreen(navController: NavController){

    var password by remember { mutableStateOf("") }
    var userList by remember { mutableStateOf<List<User>>(emptyList()) }
    var matchedUser by remember { mutableStateOf<User?>(null) }
    val context = LocalContext.current
    val sCurveShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(0f, size.height * 0.8f)
        cubicTo(
            size.width * 0.25f, size.height * 0.6f,
            size.width * 0.75f, size.height,
            size.width, size.height * 0.8f
        )
        lineTo(size.width, 0f)
        close()
    }


    LaunchedEffect(Unit) {
        try {
            when (val result = fetchUsers()) {
                is DataState.SuccessUsers -> userList = result.data
                is DataState.Failure -> Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                DataState.Empty -> Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
                else -> {}
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error fetching users", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(id = R.color.no_comment_gray))
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_cover),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(sCurveShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.no_comment_logo),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Pristupni kod trenera") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    matchedUser = userList.find { it.password == password }
                    if (matchedUser != null) {
                        Toast.makeText(context, "Dobrodošao/la ${matchedUser!!.fullName}", Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.MainScreen.route)
                    } else {
                        Toast.makeText(context, "Pogrešna šifra", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0F8070),
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            SendEmailButton()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_comment_logo_black),
                contentDescription = "Small Logo",
                modifier = Modifier
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "© 2023",
                color = Color.Gray
            )
        }

    }
}
