import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.no_comment_logo),
                contentDescription = "Header Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
        item {
            Button(
                onClick = {
                    navController.navigate(Screen.AdvancedJuniorsScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0EA570),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Pioniri",
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

        }
        item {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
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

        }

        item {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
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

        }
    }
}
