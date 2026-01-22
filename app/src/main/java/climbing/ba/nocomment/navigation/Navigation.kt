package climbing.ba.nocomment.navigation

import MainScreen
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import climbing.ba.nocomment.screens.AddMemberScreen
//import climbing.ba.nocomment.screens.AdvancedJuniorsScreen
import climbing.ba.nocomment.screens.EditMemberScreen
import climbing.ba.nocomment.screens.GroupsScreen
//import climbing.ba.nocomment.screens.JuniorScreen
import climbing.ba.nocomment.screens.LoginScreen
//import climbing.ba.nocomment.screens.PubertetlijeScreen
//import climbing.ba.nocomment.screens.RekreativciScreen
import climbing.ba.nocomment.screens.SettingsScreen
//import climbing.ba.nocomment.screens.StarijaGrupaScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    context: Context,
    lifecycleOwner: LifecycleOwner
) {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {

        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController = navController)
        }
//
//        composable(
//            route = Screen.JuniorScreen.route
//        ) {
//            JuniorScreen(navController = navController)
//        }
//
//        composable(
//            route = Screen.AdvancedJuniorsScreen.route
//        ) {
//            AdvancedJuniorsScreen(navController = navController)
//        }
//
//        composable(
//            route = Screen.StarijaGrupaScreen.route
//        ) {
//            StarijaGrupaScreen(navController = navController)
//        }
//
//        composable(
//            route = Screen.RekreativciScreen.route
//        ) {
//            RekreativciScreen(navController = navController)
//        }
//
//        composable(
//            route = Screen.PubertetlijeScreen.route
//        ) {
//            PubertetlijeScreen(navController = navController)
//        }

        composable(
            route = Screen.AddMemberScreen.route
        ) {
            AddMemberScreen(navController = navController)
        }

        composable(
            route = Screen.GroupsScreen.route
        ) {
            GroupsScreen(navController = navController)
        }

        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(navController = navController)
        }

        composable(
            route = Screen.EditMemberScreen.route + "/{memberId}",
            arguments = listOf(
                navArgument("memberId"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        )
        { entry ->
            entry.arguments?.getString("memberId")?.let { EditMemberScreen(navController,memberId = it) }
        }

    }
}