package climbing.ba.nocomment.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import climbing.ba.nocomment.sealed.DataState
import fetchDataFromFirebase

class AdvancedJuniorsView : ViewModel() {

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchDataFromFirebase(response)
    }
}