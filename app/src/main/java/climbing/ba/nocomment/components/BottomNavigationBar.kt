package climbing.ba.nocomment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        val items = listOf(
            Triple(
                "Home",
                painterResource(id = R.drawable.skenderija_logo),
                Screen.MainScreen.route
            ),
            Triple(
                "10 termina",
                painterResource(id = R.drawable.session_cards),
                Screen.SessionCardsScreen.route
            ),
            Triple(
                "Dodaj člana",
                painterResource(id = R.drawable.add),
                Screen.AddMemberScreen.route
            ),
            Triple("Grupe", painterResource(id = R.drawable.group), Screen.GroupsScreen.route)
        )

        items.forEach { (label, icon, route) ->
            val selected = currentRoute == route

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { navController.navigate(route) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(width = 75.dp, height = 40.dp)
                ) {
                    if (selected) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    color = Color(0xFFB8E6C7).copy(alpha = 0.9f),
                                    shape = CircleShape
                                )
                        )
                    }
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        tint = if (selected) colorResource(id = R.color.no_comment_dark_gray) else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )

                }

                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = label,
                    fontSize = 15.sp,
                    color = if (selected) colorResource(id = R.color.no_comment_dark_gray) else Color.Gray
                )
            }
        }
    }
}
