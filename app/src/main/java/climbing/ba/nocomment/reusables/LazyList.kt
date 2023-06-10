package climbing.ba.nocomment.reusables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import climbing.ba.nocomment.model.Member

@Composable
fun ShowLazyList(members: List<Member>) {
    LazyColumn {
        items(members) { member ->
            CardItem(member)
        }
    }
}
