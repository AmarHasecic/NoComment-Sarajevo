package climbing.ba.nocomment.navigation

import MainScreen
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import climbing.ba.nocomment.screens.AdvancedJuniorsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    context: Context,
    lifecycleOwner: LifecycleOwner
) {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

           composable(
                route = Screen.MainScreen.route
            ) {
               MainScreen(navController = navController)
            }

        composable(
            route = Screen.AdvancedJuniorsScreen.route
        ) {
            AdvancedJuniorsScreen(navController = navController)
        }

    }
}