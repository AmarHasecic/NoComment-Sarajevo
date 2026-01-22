package climbing.ba.nocomment.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import climbing.ba.nocomment.model.MemberType
import climbing.ba.nocomment.navigation.Screen
import climbing.ba.nocomment.components.BottomNavigationBar

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMemberScreen(navController: NavController) {
    val fullName = remember { mutableStateOf("") }
    val imeRoditelja = remember { mutableStateOf("") }
    val brojTelefonaRoditelja = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf(MemberType.JUNIOR) }
    val expanded = remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novi kolačić") },
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Podaci za registraciju člana")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                label = { Text(text = "Ime i prezime") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = imeRoditelja.value,
                onValueChange = { imeRoditelja.value = it },
                label = { Text(text = "Ime roditelja") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = brojTelefonaRoditelja.value,
                onValueChange = { brojTelefonaRoditelja.value = it },
                label = { Text(text = "Broj telefona roditelja") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                OutlinedTextField(
                    value = selectedType.value.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Odaberi Tip Člana") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded.value = true }
                )
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    MemberType.values().forEach { type ->
                        DropdownMenuItem(onClick = {
                            selectedType.value = type
                            expanded.value = false
                        }) {
                            Text(text = type.toString())
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val name = fullName.value
                    val parentName = imeRoditelja.value
                    val parentPhone = brojTelefonaRoditelja.value

                    if (name.isNotEmpty()) {
                        val member = Member(
                            id = "",
                            fullName = name,
                            imeRoditelja = parentName,
                            brojTelefonaRoditelja = parentPhone,
                            payments = emptyList(),
                            type = selectedType.value
                        )

                        addMemberToDatabase(member, context)
                        navController.navigate(Screen.MainScreen.route)
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
}
