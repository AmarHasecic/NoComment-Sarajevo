package climbing.ba.nocomment.reusables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
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
                    Icons.Default.Face,
                    contentDescription = "Grupe",
                    tint = if (currentRoute == Screen.GroupsScreen.route) Color.Black else Color.Gray
                )
            },
            label = { Text("Grupe") }
        )
    }
}

