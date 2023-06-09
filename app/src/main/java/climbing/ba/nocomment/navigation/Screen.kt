package climbing.ba.nocomment.navigation

sealed class Screen (val route : String){

    object MainScreen : Screen("MainPage")
    object AdvancedJuniorsScreen : Screen("AdvancedJuniorsScreen")
}