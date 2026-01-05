package climbing.ba.nocomment.reusables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.model.Member

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowLazyList(
    members: MutableList<Member>,
    navController: NavController,
    year: Int
) {
    LazyColumn {
        items(members) { member ->
            CardItem(
                member = member,
                navController = navController,
                year = year
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
            }
        }
    }
}
