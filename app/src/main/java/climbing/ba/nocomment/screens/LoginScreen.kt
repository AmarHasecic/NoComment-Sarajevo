package climbing.ba.nocomment.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
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

    LaunchedEffect(Unit) {
        try {
            when (val result = fetchUsers()) {
                is DataState.SuccessUsers -> {
                    userList = result.data
                }
                is DataState.Failure -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                DataState.Empty -> {
                    Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error fetching usrers", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
                           .padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_comment_logo),
                    contentDescription = "Header Photo",
                    modifier = Modifier.fillMaxWidth().padding(top = 100.dp, bottom = 20.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {

                        matchedUser = userList.find { it.password == password }
                        if (matchedUser != null) {
                            Toast.makeText(context, "Dobrodošao " + matchedUser!!.fullName, Toast.LENGTH_LONG).show()
                            navController.navigate(Screen.MainScreen.route)
                        } else {
                            Toast.makeText(context, "Pogrešnas šifra", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF0EA570),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }
            }
        }
    }

}