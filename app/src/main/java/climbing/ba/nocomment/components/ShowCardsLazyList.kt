package climbing.ba.nocomment.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import climbing.ba.nocomment.R
import climbing.ba.nocomment.model.TenSessionCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowCardsLazyList(
    cards: SnapshotStateList<TenSessionCard>,
    showArchived: Boolean,
    searchQuery: String,
    onCardDeleted: (TenSessionCard) -> Unit,
    onCardChanged: (TenSessionCard) -> Unit
) {
    val filteredCards = cards.filter { card ->
        val archivedMatch = if (showArchived) card.archived else !card.archived
        val searchMatch = card.memberName.contains(searchQuery, ignoreCase = true)
        archivedMatch && searchMatch
    }

    LazyColumn(
        modifier = Modifier.background(color = colorResource(id = R.color.no_comment_gray))
            .padding(top=5.dp)
    ) {
        items(
            items = filteredCards,
            key = { it.id }
        ) { card ->
            TenSessionCardItem(
                card = card,
                onCardDeleted = onCardDeleted,
                onCardChanged = onCardChanged
            )
        }
        item {
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}