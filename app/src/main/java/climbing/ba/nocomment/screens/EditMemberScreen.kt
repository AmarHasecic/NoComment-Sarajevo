package climbing.ba.nocomment.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.database.addMemberToDatabase
import climbing.ba.nocomment.database.fetchMemberById
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.MemberType
import climbing.ba.nocomment.sealed.DataState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditMemberScreen(navController: NavController, memberId: String) {
    var dataState by remember { mutableStateOf<DataState>(DataState.Loading) }
    var member by remember { mutableStateOf<Member?>(null) }
    var fullName by remember { mutableStateOf("") }
    var imeRoditelja by remember { mutableStateOf("") }
    var brojTelefonaRoditelja by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<MemberType?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(memberId) {
        dataState = fetchMemberById(memberId)
        if (dataState is DataState.SuccessMember) {
            member = (dataState as DataState.SuccessMember).data
            member?.let {
                fullName = it.fullName
                imeRoditelja = it.imeRoditelja
                brojTelefonaRoditelja = it.brojTelefonaRoditelja
                selectedType = it.type
            }
        }
    }

    when (dataState) {
        is DataState.Loading -> {
            CircularProgressIndicator()
        }
        is DataState.Empty -> {
            Text("No member found with ID: $memberId")
        }
        is DataState.Failure -> {
            Text("Error: ${(dataState as DataState.Failure).message}")
        }
        is DataState.SuccessMember -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Update Member Information")

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text(text = "Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = imeRoditelja,
                    onValueChange = { imeRoditelja = it },
                    label = { Text(text = "Parent's Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = brojTelefonaRoditelja,
                    onValueChange = { brojTelefonaRoditelja = it },
                    label = { Text(text = "Parent's Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedType?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Member Type") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        MemberType.values().forEach { type ->
                            DropdownMenuItem(onClick = {
                                selectedType = type
                                expanded = false
                            }) {
                                Text(text = type.name)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (fullName.isNotEmpty()) {
                            val newMember = Member(
                                id = member?.id ?: "",
                                fullName = fullName,
                                imeRoditelja = imeRoditelja,
                                brojTelefonaRoditelja = brojTelefonaRoditelja,
                                payments = member?.payments ?: emptyList(),
                                type = selectedType
                            )

                            addMemberToDatabase(newMember, context)
                            navController.popBackStack()
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
                        text = "Update",
                        color = Color.White
                    )
                }
            }
        }

        //Ovo se nikad ne može dogoditi svakako
        is DataState.Success -> TODO()
        is DataState.SuccessUsers -> TODO()
        is DataState.SuccessCards -> TODO()
    }
}
