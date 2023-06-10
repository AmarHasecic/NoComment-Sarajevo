package climbing.ba.nocomment.reusables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import climbing.ba.nocomment.model.Member


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardItem(member: Member) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = member.fullName,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color(0xFF0EA570)),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                ButtonGrid(member)
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonGrid(member: Member) {
    val buttonColors = remember {
        mutableStateListOf(
            Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242),
            Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242), Color(0xFFEB4242)
        )
    }

    // Update buttonColors based on member's payments
    member.payments.forEach { payment ->
        val monthIndex = payment.month.value-1
            buttonColors[monthIndex] = if (payment.amount > 0) Color.Green else Color(0xFFEB4242)

    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            buttonColors.subList(0, 6).forEachIndexed { index, color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(color)
                        .clickable {
                            buttonColors[index] = if (color == Color(0xFFEB4242)) Color.Green else Color(0xFFEB4242)
                            // Update payment amount based on button click
                            member.payments[index].amount = if (color == Color(0xFFEB4242)) 50 else 0

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
                        .background(color)
                        .clickable {
                            buttonColors[index + 6] = if (color == Color(0xFFEB4242)) Color.Green else Color(0xFFEB4242)
                            // Update payment amount based on button click
                            val monthIndex = index + 6
                            member.payments[monthIndex].amount = if (color == Color(0xFFEB4242)) 50 else 0

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
}



val monthNames = listOf(
    "Jan", "Feb", "Mar", "Apr", "Maj", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)