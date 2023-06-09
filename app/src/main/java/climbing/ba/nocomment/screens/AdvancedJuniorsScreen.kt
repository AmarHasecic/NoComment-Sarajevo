package climbing.ba.nocomment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState


@Composable
fun AdvancedJuniorsScreen(navController: NavController) {

    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }

    LaunchedEffect(Unit) {
        dataState.value = fetchData()
    }

    when (val state = dataState.value) {
        is DataState.Success -> {
            val memberList = state.data
            ShowLazyList(state.data)
        }
        is DataState.Failure -> {
            val errorMessage = state.message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
        DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        DataState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nema dodanih članova kluba",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {



        // Circular plus floating button
        FloatingActionButton(
            onClick = {
                navController.navigate("AddMemberScreen")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = Color(0xFF0EA570)

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}



@Composable
fun ShowLazyList(members: MutableList<Member>) {
    LazyColumn {
        items(members) { member ->
            CardItem(member)
        }
    }
}

@Composable
fun CardItem(member: Member) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = member.fullName!!,
                fontSize = MaterialTheme.typography.h5.fontSize,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.LightGray),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

    }
}
