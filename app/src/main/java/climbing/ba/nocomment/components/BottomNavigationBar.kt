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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import climbing.ba.nocomment.R
import climbing.ba.nocomment.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val interactionSource = remember { MutableInteractionSource() }

    val items = remember {
        listOf(
            BottomNavItem(
                label = "Home",
                icon = R.drawable.skenderija_logo,
                route = Screen.MainScreen.route
            ),
            BottomNavItem(
                label = "10 termina",
                icon = R.drawable.session_cards,
                route = Screen.SessionCardsScreen.route
            ),
            BottomNavItem(
                label = "Dodaj člana",
                icon = R.drawable.add,
                route = Screen.AddMemberScreen.route
            ),
            BottomNavItem(
                label = "Grupe",
                icon = R.drawable.group,
                route = Screen.GroupsScreen.route
            )
        )
    }

    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {

        items.forEach { item ->
            val selected = currentRoute == item.route
            val iconPainter = painterResource(item.icon)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (!selected) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier.size(width = 75.dp, height = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (selected) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Color(0xFFB8E6C7).copy(alpha = 0.9f),
                                    CircleShape
                                )
                        )
                    }

                    Icon(
                        painter = iconPainter,
                        contentDescription = item.label,
                        tint = if (selected)
                            colorResource(R.color.no_comment_dark_gray)
                        else Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(Modifier.height(3.dp))

                Text(
                    text = item.label,
                    fontSize = 15.sp,
                    color = if (selected)
                        colorResource(R.color.no_comment_dark_gray)
                    else Color.Gray
                )
            }
        }
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String
)
