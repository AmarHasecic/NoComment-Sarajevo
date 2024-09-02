package climbing.ba.nocomment.reusables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.database.deleteMember
import climbing.ba.nocomment.database.updateMember
import climbing.ba.nocomment.model.Member

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardItem(
    member: Member,
    members: MutableList<Member>,
    navController: NavController
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val showPayments = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFF0EA570))
                        .clickable {
                            showPayments.value = !showPayments.value
                        }
                ) {
                    Text(
                        text = member.fullName,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    if (!showPayments.value) {
                        Text(
                            text = "Tap for details",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                    if (showPayments.value) {
                        IconButton(
                            onClick = {
                                showDialog.value = true
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = showPayments.value) {
                    ButtonGrid(member)
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Delete") },
            text = { Text("Do you want to delete this member?") },
            confirmButton = {
                Button(onClick = {
                    deleteMember(member, context)
                    members.remove(member)
                    showDialog.value = false
                    navController.navigate("MainScreen")
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonGrid(member: Member) {
    val showDialog = remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val buttonColors = remember {
        mutableStateListOf(
            Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242),
            Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242),
            Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242)
        )
    }

    member.payments.forEach { payment ->
        val monthIndex = payment.month.value - 1
        buttonColors[monthIndex] = if (payment.amount > 0) Color.Green else Color(0xFFEB4242)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color(0x37567E59))
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            buttonColors.subList(0, 6).forEachIndexed { index, color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            buttonColors[index] =
                                if (color == Color(0xFFEB4242)) Color.Green else Color(0xFFEB4242)
                            member.payments[index].amount =
                                if (color == Color(0xFFEB4242)) 50 else 0
                            updateMember(member, context)
                        }
                ) {
                    Text(
                        text = monthNames[index],
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            buttonColors.subList(6, 12).forEachIndexed { index, color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            val monthIndex = index + 6
                            member.payments[monthIndex].amount =
                                if (color == Color(0xFFEB4242)) 60 else 0
                            buttonColors[index + 6] =
                                if (color == Color(0xFFEB4242)) Color.Green else Color(0xFFEB4242)
                            updateMember(member, context)
                        }
                ) {
                    Text(
                        text = monthNames[index + 6],
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Update") },
            text = { Text("Do you want to update the payment?") },
            confirmButton = {
                Button(onClick = {
                    updateMember(member, context)
                    showDialog.value = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}

val monthNames = listOf(
    "Jan", "Feb", "Mar", "Apr", "Maj", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)
