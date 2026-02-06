package climbing.ba.nocomment.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.components.BottomNavigationBar
import climbing.ba.nocomment.database.addMemberToDatabase
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.MemberType
import climbing.ba.nocomment.navigation.Screen

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
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(colorResource(id = R.color.no_comment_green))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_comment_logo_black),
                        contentDescription = "Header Photo",
                        modifier = Modifier.size(height = 70.dp, width = 140.dp)
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.no_comment_gray))
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Registracija člana",
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = "Ime člana je obavezno polje. Za kategorije juniori, napredni juniori i pubertetlije, potrebno je da se unesu podaci o roditelju.",
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = fullName.value,
                    onValueChange = { fullName.value = it },
                    label = { Text(text = "Ime i prezime") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imeRoditelja.value,
                    onValueChange = { imeRoditelja.value = it },
                    label = { Text(text = "Ime roditelja") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = brojTelefonaRoditelja.value,
                    onValueChange = { brojTelefonaRoditelja.value = it },
                    label = { Text(text = "Broj telefona roditelja") },
                    modifier = Modifier.fillMaxWidth()
                )
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
                        backgroundColor = colorResource(R.color.no_comment_green),
                        contentColor = colorResource(R.color.no_comment_gray)
                    )
                ) {
                    Text(
                        text = "Dodaj",
                        color = colorResource(R.color.no_comment_gray)
                    )
                }
            }
        }
    }
}
