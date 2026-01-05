//noinspection UsingMaterialAndMaterial3Libraries
package climbing.ba.nocomment.reusables

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.database.deleteMember
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardItem(
    member: Member,
    members: MutableList<Member>,
    navController: NavController
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val flipped = remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (flipped.value) 180f else 0f
    ).value

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(10.dp)
            .clickable { flipped.value = !flipped.value }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        elevation = 3.dp,
        shape = RoundedCornerShape(11.dp)
    ) {
        if (rotation <= 90f) {
            FrontSide(member = member, context = context)
        } else {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f
                }
            ) {
                BackSide(member = member, navController = navController, showDialog = showDialog)
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Delete", color = Color.White) },
            text = { Text("Do you want to delete this member?", color = Color.White) },
            confirmButton = {
                Button(onClick = {
                    deleteMember(member, context)
                    showDialog.value = false
                    navController.navigate(Screen.MainScreen.route)
                }) { Text("Yes") }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) { Text("No") }
            }
        )
    }
}

@Composable
fun FrontSide(member: Member, context: android.content.Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
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
                            modifier = Modifier.fillMaxWidth()
                                .padding(end = 40.dp)
                        ) {
                            Text(member.fullName, color = Color.Black)
                            Divider(
                                color = Color.Black,
                                thickness = 1.dp
                            )
                        }
                    }
                    if (member.imeRoditelja.isNotEmpty() && member.brojTelefonaRoditelja.isNotEmpty()) {

                    Row (

                    ){
                        Column {
                            Row(
                                modifier = Modifier.padding(top = 40.dp)
                            ) {
                                Text("Ime roditelja: ", color = Color.Black, fontSize = 18.sp)
                                Text(member.imeRoditelja, color = Color.Black)
                            }

                            Row(modifier = Modifier.padding(top = 6.dp)) {
                                IconButton(
                                    onClick = {
                                        val phoneNumber = member.brojTelefonaRoditelja
                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                            data = android.net.Uri.parse("tel:$phoneNumber")
                                        }
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Call",
                                        tint = Color.Black
                                    )
                                }

                                Text(
                                    member.brojTelefonaRoditelja,
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
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
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BackSide(
    member: Member,
    navController: NavController,
    showDialog: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxSize()
        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = member.fullName,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 22.sp
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
        ButtonGrid(member)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonGrid(member: Member) {
    val buttonPaidState = remember {
        mutableStateListOf(*member.payments.map { it.amount > 0 }.toTypedArray())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            member.payments.subList(0, 6).forEachIndexed { index, payment ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                        .fillMaxHeight()
                        .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .clickable {
                            buttonPaidState[index] = !buttonPaidState[index]
                            payment.amount = if (buttonPaidState[index]) 50 else 0
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (buttonPaidState[index]) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Paid",
                                tint = Color.Green,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        Text(
                            text = monthNames[index],
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            member.payments.subList(6, 12).forEachIndexed { index, payment ->
                val monthIndex = index + 6
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                        .fillMaxHeight()
                        .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .clickable {
                            buttonPaidState[monthIndex] = !buttonPaidState[monthIndex]
                            payment.amount = if (buttonPaidState[monthIndex]) 50 else 0
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (buttonPaidState[monthIndex]) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Paid",
                                tint = Color.Green,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        Text(
                            text = monthNames[monthIndex],
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }
        }
    }
}


val monthNames = listOf(
    "Jan", "Feb", "Mar", "Apr", "Maj", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

