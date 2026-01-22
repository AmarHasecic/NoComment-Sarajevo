//package climbing.ba.nocomment.screens
//
//import android.annotation.SuppressLint
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import climbing.ba.nocomment.database.fetchData
//import climbing.ba.nocomment.model.MemberType
//import climbing.ba.nocomment.reusables.SearchBar
//import climbing.ba.nocomment.reusables.ShowLazyList
//import climbing.ba.nocomment.sealed.DataState
//import java.time.Year
//
//@SuppressLint("MutableCollectionMutableState")
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun AdvancedJuniorsScreen(navController: NavController) {
//    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
//    val searchQuery = remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        dataState.value = fetchData()
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Napredni Juniori") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//                },
//                backgroundColor = Color.White
//            )
//        }
//    ) { innerPadding ->
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 16.dp, vertical = 16.dp)
//        ) {
//            when (val state = dataState.value) {
//                is DataState.Success -> {
//                    var memberList by remember {
//                        mutableStateOf(
//                            state.data.filter { it.type == MemberType.NAPREDNI_JUNIOR }
//                                .toMutableList()
//                        )
//                    }
//
//                    Column {
//                        SearchBar(searchQuery.value) { newQuery ->
//                            searchQuery.value = newQuery
//                        }
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        ShowLazyList(
//                            memberList.filter { member ->
//                                member.fullName.contains(searchQuery.value, ignoreCase = true)
//                            }.toMutableList(),
//                            navController,
//                            year = Year.now().value
//                        )
//                    }
//                }
//
//                is DataState.Failure -> {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = state.message,
//                            fontSize = MaterialTheme.typography.h5.fontSize
//                        )
//                    }
//                }
//
//                DataState.Loading -> {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                }
//
//                DataState.Empty -> {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Nema dodanih članova kluba",
//                            fontSize = MaterialTheme.typography.h5.fontSize
//                        )
//                    }
//                }
//
//                is DataState.SuccessMember -> TODO()
//                is DataState.SuccessUsers -> TODO()
//            }
//        }
//    }
//}
