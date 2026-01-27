package climbing.ba.nocomment.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.components.BottomNavigationBar
import climbing.ba.nocomment.components.LoadingAnimation
import climbing.ba.nocomment.components.SearchBar
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState
import kotlinx.coroutines.delay

@Composable
fun SessionCardsScreen(navController: NavController) {
    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }
    val memberList = remember { mutableStateListOf<Member>() }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        val state = fetchData()
        val elapsed = System.currentTimeMillis() - startTime
        val minLoadingTime = 1700L
        //Just for animation purposes
        if (elapsed < minLoadingTime) {
            delay(minLoadingTime - elapsed)
        }

        when (state) {
            is DataState.Success -> {
                memberList.clear()
                memberList.addAll(state.data)
                dataState.value = state
            }
            else -> dataState.value = state
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F8070))
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
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(id = R.color.gray))
        ) {
            when (val state = dataState.value) {
                is DataState.Success -> {
                 //TODO: Prikazi listu kartica
                }

                is DataState.Failure -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message, fontSize = MaterialTheme.typography.h5.fontSize)
                }

                DataState.Loading -> {
                    LoadingAnimation()
                }

                DataState.Empty -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Nema kartica...",
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                }

                else -> {}
            }
        }
    }
}
