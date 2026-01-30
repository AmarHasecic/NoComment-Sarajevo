package climbing.ba.nocomment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import climbing.ba.nocomment.R

@Composable
fun ToggleArchived(showArchived: MutableState<Boolean>) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { showArchived.value = false }
                .background(
                    colorResource(id = R.color.no_comment_green),
                    shape = RoundedCornerShape(
                        bottomEnd = 27.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (!showArchived.value) {
                Box(
                    Modifier.fillMaxSize()
                        .background(
                            colorResource(id = R.color.no_comment_gray_no_transparent),
                            shape = RoundedCornerShape(
                                topEnd = 27.dp
                            )
                        )
                ) {
                    Box(
                        Modifier.fillMaxSize()
                            .padding(horizontal = 5.dp)
                            .padding(top = 5.dp)
                            .background(
                                colorResource(R.color.no_comment_highlight_green).copy(alpha = 0.9f),
                                shape = RoundedCornerShape(25.dp)
                            )
                    ) {}
                }
            }
            Text(
                text = "Aktivne",
                fontWeight = FontWeight.Bold,
                color = if (!showArchived.value) colorResource(id = R.color.no_comment_dark_gray) else colorResource(id = R.color.no_comment_gray)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { showArchived.value = true }
                .background(
                    colorResource(id = R.color.no_comment_green),
                    shape = RoundedCornerShape(
                        bottomStart = 27.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (showArchived.value) {
                Box(
                    Modifier.fillMaxSize()
                        .background(
                            colorResource(id = R.color.no_comment_gray_no_transparent),
                            shape = RoundedCornerShape(
                                topStart = 27.dp
                            )
                        )
                ) {
                    Box(
                        Modifier.fillMaxSize()
                            .padding(horizontal = 5.dp)
                            .padding(top = 5.dp)
                            .background(
                                colorResource(R.color.no_comment_highlight_green).copy(alpha = 0.9f),
                                shape = RoundedCornerShape(25.dp)
                            )
                    ) {}
                }
            }
            Text(
                text = "Arhivirane",
                fontWeight = FontWeight.Bold,
                color = if (showArchived.value) colorResource(id = R.color.no_comment_dark_gray) else colorResource(id = R.color.no_comment_gray)
            )
        }
    }
}