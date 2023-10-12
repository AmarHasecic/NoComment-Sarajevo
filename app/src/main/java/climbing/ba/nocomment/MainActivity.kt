package climbing.ba.nocomment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import climbing.ba.nocomment.navigation.Navigation
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

