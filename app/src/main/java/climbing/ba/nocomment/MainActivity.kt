package climbing.ba.nocomment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.navigation.Navigation
import climbing.ba.nocomment.sealed.DataState
import climbing.ba.nocomment.ui.theme.NoCommentTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            NoCommentTheme {
                val lifecycleOwner = this
                Navigation(applicationContext, lifecycleOwner = lifecycleOwner)
                }
            }
        }
    }

