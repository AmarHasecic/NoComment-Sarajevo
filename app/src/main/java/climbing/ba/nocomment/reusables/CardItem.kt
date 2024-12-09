package climbing.ba.nocomment.reusables

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import climbing.ba.nocomment.navigation.Screen

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
            .padding(10.dp) ,
        elevation = 3.dp,
        shape = RoundedCornerShape(11.dp)
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
                    if (!showPayments.value) {
                        Text(
                            text = member.fullName,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 10.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        if(member.imeRoditelja!="" && member.brojTelefonaRoditelja!="") {
                            Column(
                                modifier = Modifier.padding(start = 16.dp, top = 50.dp)
                            ) {
                                Spacer(modifier = Modifier.padding(vertical = 7.dp))
                                Row() {
                                    Text(
                                        "Ime roditelja: ",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = member.imeRoditelja,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    )
                                }
                                Spacer(modifier = Modifier.padding(vertical = 3.dp))
                                Row() {

                                    IconButton(
                                        onClick = {
                                            val phoneNumber = member.brojTelefonaRoditelja
                                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                                data = android.net.Uri.parse("tel:$phoneNumber")
                                            }
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Call,
                                            contentDescription = "Call",
                                            tint = Color.White,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                    Text(
                                        text = member.brojTelefonaRoditelja,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Dodirni da vidiš plaćanja",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(start = 10.dp, bottom = 4.dp),
                            textAlign = TextAlign.Center,
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    }
                    if (showPayments.value) {

                        Text(
                            text = member.fullName,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 10.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 5.dp)

                        ){

                            IconButton(
                                onClick = {
                                    //TODO: Napraviti screen za edit clana
                                },
                                modifier = Modifier
                                    .size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                            IconButton(
                                onClick = {
                                    showDialog.value = true
                                },
                                modifier = Modifier
                                    .size(32.dp)
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
            title = { Text("Delete", color = Color.White) },
            text = { Text("Do you want to delete this member?", color = Color.White) },
            confirmButton = {
                Button(onClick = {
                    deleteMember(member, context)
                    showDialog.value = false
                    navController.navigate(Screen.MainScreen.route)
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
            title = { Text("Update", color = Color.White) },
            text = { Text("Do you want to update the payment?", color = Color.White) },
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
