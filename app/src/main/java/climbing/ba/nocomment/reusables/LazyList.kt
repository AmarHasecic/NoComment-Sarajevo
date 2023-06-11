package climbing.ba.nocomment.reusables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import climbing.ba.nocomment.model.Member

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowLazyList(members: List<Member>) {
    LazyColumn {
        items(members) { member ->
            CardItem(member)
        }
    }
}
