package climbing.ba.nocomment.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    BottomNavigation(backgroundColor = Color.White) {

        BottomNavigationItem(
            selected = currentRoute == Screen.MainScreen.route,
            onClick = { navController.navigate(Screen.MainScreen.route) },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (currentRoute == Screen.MainScreen.route) Color.Black else Color.Gray
                )
            },
            label = { Text("Home") }
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.SessionCardsScreen.route,
            onClick = { navController.navigate(Screen.SessionCardsScreen.route) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.session_cards),
                    contentDescription = "Kartice 10 termina",
                    modifier = Modifier.size(24.dp),
                    tint = if (currentRoute == Screen.SessionCardsScreen.route) Color.Black else Color.Gray
                )
            },
            label = { Text("10 termina") }
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.AddMemberScreen.route,
            onClick = { navController.navigate(Screen.AddMemberScreen.route) },
            icon = {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = "Add",
                    tint = if (currentRoute == Screen.AddMemberScreen.route) Color.Black else Color.Gray
                )
            },
            label = { Text("Dodaj člana") }
        )

        BottomNavigationItem(
            selected = currentRoute == Screen.GroupsScreen.route,
            onClick = { navController.navigate(Screen.GroupsScreen.route) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.group),
                    contentDescription = "Grupe",
                    modifier = Modifier.size(24.dp),
                    tint = if (currentRoute == Screen.GroupsScreen.route) Color.Black else Color.Gray
                )
            },
            label = { Text("Grupe") }
        )
    }
}

