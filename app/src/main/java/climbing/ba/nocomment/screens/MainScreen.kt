//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.reusables.BottomNavigationBar
import climbing.ba.nocomment.reusables.SearchBar
import climbing.ba.nocomment.reusables.ShowLazyList
import climbing.ba.nocomment.sealed.DataState
import java.time.Year

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen(navController: NavController) {

    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }
    val selectedYear = remember { mutableStateOf(Year.now().value) }
    var expanded by remember { mutableStateOf(false) }

    // This will hold the live member list
    val memberList = remember { mutableStateListOf<Member>() }

    LaunchedEffect(Unit) {
        when (val state = fetchData()) {
            is DataState.Success -> {
                memberList.clear()
                memberList.addAll(state.data)
                dataState.value = state
            }
            else -> dataState.value = state
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 10.dp)
            ) {
                TextButton(onClick = { expanded = true }) {
                    Text(
                        text = selectedYear.value.toString(),
                        color = Color.DarkGray,
                        fontSize = 20.sp
                    )
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    val currentYear = Year.now().value
                    (currentYear - 2..currentYear).forEach { year ->
                        DropdownMenuItem(onClick = {
                            selectedYear.value = year
                            expanded = false
                        }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = year.toString(), color = Color.DarkGray, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_comment_logo),
                        contentDescription = "Header Photo",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }

                Divider(thickness = 1.dp)

                when (val state = dataState.value) {
                    is DataState.Success -> {
                        ShowLazyList(
                            members = memberList
                                .filter { it.fullName.contains(searchQuery.value, ignoreCase = true) }
                                .toMutableList(),
                            navController = navController,
                            year = selectedYear.value,
                            onMemberDeleted = { deletedMember ->
                                memberList.remove(deletedMember)
                            },
                            onMemberUpdated = { updatedMember ->
                                val index = memberList.indexOfFirst { it.id == updatedMember.id }
                                if (index >= 0) {
                                    memberList[index] = updatedMember
                                }
                            }
                        )
                    }

                    is DataState.Failure -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text(state.message, fontSize = MaterialTheme.typography.h5.fontSize) }

                    DataState.Loading -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }

                    DataState.Empty -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text("Nema dodanih članova kluba", fontSize = MaterialTheme.typography.h5.fontSize) }

                    else -> {}
                }
            }
        }
    }
}
