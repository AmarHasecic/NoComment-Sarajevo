import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.navigation.Screen


@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_comment_logo),
                    contentDescription = "Header Photo",
                    modifier = Modifier.fillMaxWidth().
                    padding(top = 100.dp, bottom = 20.dp)
                )
            }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.JuniorScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0EA570),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Juniori",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.AdvancedJuniorsScreen.route)

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0EA570),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Napredni Juniori",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.StarijaGrupaScreen.route)

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0EA570),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Starija grupa",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.RekreativciScreen.route)

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0EA570),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Rekreativci",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.PubertetlijeScreen.route)

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF0EA570),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Pubertetlije",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
