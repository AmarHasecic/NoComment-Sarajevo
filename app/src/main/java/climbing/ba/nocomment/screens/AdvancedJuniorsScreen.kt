package climbing.ba.nocomment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState
import climbing.ba.nocomment.viewmodels.AdvancedJuniorsView


@Composable
fun AdvancedJuniorsScreen(navController: NavController) {

   // val viewModel: AdvancedJuniorsView by viewModels()
    //SetData(viewModel)
}


@Composable
fun SetData(viewModel: AdvancedJuniorsView) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DataState.Success -> {
            ShowLazyList(result.data)
        }
        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                )
            }
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
