package climbing.ba.nocomment.navigation

sealed class Screen (val route : String){

    object MainScreen : Screen("MainPage")
    object JuniorScreen : Screen("JuniorScreen")
    object RekreativciScreen : Screen("RekreativciScreen")
    object AdvancedJuniorsScreen : Screen("AdvancedJuniorsScreen")
    object StarijaGrupaScreen : Screen("StarijaGrupaScreen")
    object PubertetlijeScreen : Screen("PubertetlijeScreen")
    object AddMemberScreen : Screen("AddMemberScreen")
    object EditMemberScreen : Screen("EditMemberScreen")
    object LoginScreen : Screen("LoginScreen")
}
