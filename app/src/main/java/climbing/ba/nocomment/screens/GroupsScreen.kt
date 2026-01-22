package climbing.ba.nocomment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.components.BottomNavigationBar

data class GroupItem(val name: String, val route: String)

@Composable
fun GroupsScreen(navController: NavController) {
//    val groups = listOf(
//        GroupItem("Juniori", Screen.JuniorScreen.route),
//        GroupItem("Napredni Juniori", Screen.AdvancedJuniorsScreen.route),
//        GroupItem("Starija grupa", Screen.StarijaGrupaScreen.route),
//        GroupItem("Rekreativci", Screen.RekreativciScreen.route),
//        GroupItem("Pubertetlije", Screen.PubertetlijeScreen.route)
//    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grupe") },
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            items(groups) { group ->
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .clickable { navController.navigate(group.route) },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = group.name,
//                        color = Color.Black,
//                        style = MaterialTheme.typography.h6
//                    )
//                }
//                Divider(color = Color.LightGray, thickness = 1.dp)
//            }
            item {
                Text(
                    text = "Trenutno nije moguće prikazati ekran sa grupama. U toku su radovi na održavanju.",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .background(
                            color = Color.Yellow.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                )
            }

        }

    }
}
