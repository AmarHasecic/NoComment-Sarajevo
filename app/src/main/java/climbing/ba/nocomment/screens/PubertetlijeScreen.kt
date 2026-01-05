package climbing.ba.nocomment.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.model.MemberType
import climbing.ba.nocomment.reusables.SearchBar
import climbing.ba.nocomment.reusables.ShowLazyList
import climbing.ba.nocomment.sealed.DataState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PubertetlijeScreen(navController: NavController) {
    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        dataState.value = fetchData()
    }

    when (val state = dataState.value) {
        is DataState.Success -> {

            var memberList by remember { mutableStateOf(state.data.filter { it.type == MemberType.PUBERTETLIJA }.toMutableList()) }

            Column {

                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }
                Spacer(modifier = Modifier.height(8.dp))

                ShowLazyList(
                    memberList.filter { member ->
                        member.fullName.contains(searchQuery.value, ignoreCase = true)
                    }.toMutableList(),
                    navController
                )
            }
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

        is DataState.SuccessMember -> TODO()
        is DataState.SuccessUsers -> TODO()
    }
}
