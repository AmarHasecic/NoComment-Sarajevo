//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
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
import androidx.compose.material.CircularProgressIndicator
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
import climbing.ba.nocomment.components.ProgressIndicator
import climbing.ba.nocomment.components.SearchBar
import climbing.ba.nocomment.components.ShowLazyList
import climbing.ba.nocomment.components.YearPicker
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState
import kotlinx.coroutines.delay
import java.time.Year

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen(navController: NavController) {

    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }
    val selectedYear = remember { mutableStateOf(Year.now().value) }
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
                modifier = Modifier.fillMaxWidth()
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
                    YearPicker(selectedYear = selectedYear)
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
                        ShowLazyList(
                            members = memberList
                                .filter {
                                    it.fullName.contains(
                                        searchQuery.value,
                                        ignoreCase = true
                                    )
                                }
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

                DataState.Loading -> {
                    LoadingAnimation()
                    ProgressIndicator()
                }

                DataState.Empty -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Nema dodanih članova kluba",
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                }
                else -> {}
            }
        }
    }
}
