package climbing.ba.nocomment.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import climbing.ba.nocomment.R
import climbing.ba.nocomment.database.deleteTenSessionCard
import climbing.ba.nocomment.model.Session
import climbing.ba.nocomment.model.TenSessionCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private fun isSessionNext(sessions: List<Session>, index: Int): Boolean {
    val before = sessions.take(index)
    before.forEach { session ->
        if(!session.isUsed) {
            return false
        }
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TenSessionCardItem(
    card: TenSessionCard,
    onCardChanged: (TenSessionCard) -> Unit,
    onCardDeleted: (TenSessionCard) -> Unit
) {
    val flipped = remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(if (flipped.value) 180f else 0f).value
    val sessionsState = remember { card.sessions.toMutableStateList() }
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(255.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { flipped.value = !flipped.value }
            .graphicsLayer { rotationY = rotation; cameraDistance = 12f * density },
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        if (rotation <= 90f) {
            TenSessionCardFront(card)
        } else {
            Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                TenSessionCardBack(
                    card = card,
                    showDialog = showDialog,
                    sessionsState = sessionsState,
                    onCardChanged = onCardChanged
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Brisanje kartice") },
            text = { Text("Da li želite obrisati karticu?") },
            confirmButton = {
                Button(onClick = {
                    deleteTenSessionCard(card, context)
                    showDialog.value = false
                    onCardDeleted(card)
                }) { Text("Da") }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) { Text("Ne") }
            }
        )
    }
}

@Composable
fun TenSessionCardFront(card: TenSessionCard) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Članska kartica",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 20.dp),
                    color = Color.Black
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp, top = 35.dp)) {
                Row {
                    Text("Ime i prezime: ", color = Color.Black, fontSize = 18.sp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp)
                    ) {
                        Text(card.memberName, color = Color.Black)
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, end = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_comment_logo),
            contentDescription = "Header Photo",
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer(alpha = 0.7f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TenSessionCardBack(
    card: TenSessionCard,
    showDialog: MutableState<Boolean>,
    sessionsState: SnapshotStateList<Session>,
    onCardChanged: (TenSessionCard) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = card.memberName,
                color = Color.Black,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp)
            )

            IconButton(onClick = { showDialog.value = true }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        TenSessionCardButtonGrid(
            sessionsState = sessionsState,
            card = card,
            onCardChanged = onCardChanged
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TenSessionCardButtonGrid(
    sessionsState: SnapshotStateList<Session>,
    card: TenSessionCard,
    onCardChanged: (TenSessionCard) -> Unit
) {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d/M/yy")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(2.dp)
    ) {
        sessionsState.chunked(5).forEachIndexed { rowIndex, rowSessions ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
            ) {
                rowSessions.forEachIndexed { colIndex, session ->
                    val sessionIndex = rowIndex * 5 + colIndex

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                            .fillMaxHeight()
                            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                            .clickable {
                                if (isSessionNext(sessionsState.toList(), sessionIndex)) {
                                    sessionsState[sessionIndex] =
                                        sessionsState[sessionIndex].copy(
                                            isUsed = !sessionsState[sessionIndex].isUsed,
                                            date = if (!sessionsState[sessionIndex].isUsed)
                                                LocalDate.now().toString()
                                            else null
                                        )

                                    val isNowArchived = sessionsState.all { it.isUsed }

                                    val updatedCard = card.copy(
                                        sessions = sessionsState.toList(),
                                        archived = isNowArchived
                                    )
                                    onCardChanged(updatedCard)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Oznaci prvi sljedeci slobodan termin!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (sessionIndex + 1).toString(),
                            color = colorResource(R.color.no_comment_dark_gray),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (session.isUsed) {
                            val formattedDate = session.date?.let {
                                LocalDate.parse(it, inputFormatter).format(outputFormatter)
                            }
                            Text(
                                text = formattedDate ?: "",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = colorResource(R.color.no_comment_dark_gray),
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Used",
                                tint = Color.Red.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}