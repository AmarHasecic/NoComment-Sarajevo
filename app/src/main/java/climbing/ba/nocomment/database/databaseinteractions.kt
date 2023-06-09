import androidx.compose.runtime.MutableState
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


    fun fetchDataFromFirebase(response: MutableState<DataState>) {
        val tempList = mutableListOf<Member>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference("members")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val item = DataSnap.getValue(Member::class.java)
                        if (item != null)
                            tempList.add(item)
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })
    }
