package climbing.ba.nocomment.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.components.BottomNavigationBar
import climbing.ba.nocomment.components.CreateCardDialog
import climbing.ba.nocomment.components.FloatingAddButton
import climbing.ba.nocomment.components.ProgressIndicator
import climbing.ba.nocomment.components.SearchBar
import climbing.ba.nocomment.components.ShowCardsLazyList
import climbing.ba.nocomment.components.ToggleArchived
import climbing.ba.nocomment.database.addTenSessionCard
import climbing.ba.nocomment.database.fetchTenSessionCards
import climbing.ba.nocomment.database.updateTenSessionCard
import climbing.ba.nocomment.model.Session
import climbing.ba.nocomment.model.TenSessionCard
import climbing.ba.nocomment.sealed.DataState

private fun checkIfArchived(card: TenSessionCard) : Boolean {
  card.sessions.forEach() { session ->
        if(!session.isUsed) {
            return false
        }
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionCardsScreen(navController: NavController) {
    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }
    val cards = remember { mutableStateListOf<TenSessionCard>() }
    val showArchived = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showDialogArchived by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current
    var nameArchivedTemp by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        when (val state = fetchTenSessionCards()) {
            is DataState.SuccessCards -> {
                cards.clear()
                cards.addAll(state.data)
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
                .background(color = colorResource(id = R.color.no_comment_gray))
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ){
                ToggleArchived(showArchived)
                when (val state = dataState.value) {
                    is DataState.SuccessCards -> {
                        ShowCardsLazyList(
                            cards = cards,
                            showArchived = showArchived.value,
                            searchQuery = searchQuery.value,

                            onCardDeleted = { deletedCard ->
                                cards.removeAll { it.id == deletedCard.id }
                            },

                            onCardChanged = { updatedCard ->
                                val index = cards.indexOfFirst { it.id == updatedCard.id }

                                if (index != -1) {
                                    cards[index] = updatedCard
                                }
                                //to show dialog to create new card for archived member
                                if(checkIfArchived(updatedCard)){
                                    nameArchivedTemp = updatedCard.memberName
                                    showDialogArchived = true
                                }
                                updateTenSessionCard(updatedCard, context)
                            }
                        )
                    }

                    is DataState.Failure -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(state.message, fontSize = MaterialTheme.typography.h5.fontSize)
                    }

                    DataState.Loading -> {
                        ProgressIndicator()
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
            FloatingAddButton { showDialog = true }
            CreateCardDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = { input ->
                    showDialog = false

                    if (input.isNotEmpty()) {
                        val emptySessions = List(10) { Session() }
                        val card = TenSessionCard(
                            id = "",
                            memberName = input,
                            sessions = emptySessions,
                            archived = false
                        )
                        cards.add(card)
                        addTenSessionCard(card, context)
                    }

                }
            )
            CreateCardDialog(
                showDialog = showDialogArchived,
                onDismiss = {
                    showDialogArchived = false
                    nameArchivedTemp = ""
                },
                onConfirm = { input ->
                    showDialogArchived = false

                    if (input.isNotEmpty()) {
                        val emptySessions = List(10) { Session() }
                        val card = TenSessionCard(
                            id = "",
                            memberName = input,
                            sessions = emptySessions,
                            archived = false
                        )
                        cards.add(card)
                        addTenSessionCard(card, context)
                        nameArchivedTemp = ""
                    }
                },
                name = nameArchivedTemp,
                title = "Kartica arhivirana!",
                message = "Da li želite kreirati novu karticu za istog člana?",
                textAccept = "Da",
                textDecline = "Ne"
            )
        }
    }
}
