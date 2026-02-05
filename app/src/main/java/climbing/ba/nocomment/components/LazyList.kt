package climbing.ba.nocomment.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.model.Member

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowLazyList(
    members: MutableList<Member>,
    navController: NavController,
    year: Int,
    onMemberDeleted: (Member) -> Unit,
    onMemberUpdated: (Member) -> Unit
) {
    LazyColumn (
        modifier = Modifier .background(color = colorResource(id = R.color.no_comment_gray))
            .padding(top=5.dp)
    ){
        items(
            items = members,
            key = { it.id } // <-- BITNO!!!
        ) { member ->
            CardItem(
                member = member,
                navController = navController,
                year = year,
                onMemberDeleted = onMemberDeleted,
                onMemberUpdated = onMemberUpdated
            )
        }

        item {
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}


