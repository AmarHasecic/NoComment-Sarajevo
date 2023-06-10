package climbing.ba.nocomment.database

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.sealed.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await


fun makeToast(context: Context, message: String){

    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}


suspend fun fetchData(): DataState {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val memberReference = databaseReference.child("members")

    val memberList = mutableListOf<Member>()

    return try {
        val dataSnapshot = memberReference.get().await()

        if (dataSnapshot.exists()) {
            for (childSnapshot in dataSnapshot.children) {
                val member = childSnapshot.getValue(Member::class.java)
                member?.let {
                    memberList.add(it)
                }
            }
            DataState.Success(memberList)
        } else {
            DataState.Empty
        }
    } catch (e: Exception) {
        DataState.Failure(e.message ?: "An error occurred")
    }
}

fun addMemberToDatabase(member: Member, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val memberReference = databaseReference.child("members").push() // Generate a unique key for the member
    val memberId = memberReference.key // Get the generated ID

    if (memberId != null) {
        member.id = memberId
    } // Assign the generated ID to the member object

    memberReference.setValue(member)
        .addOnSuccessListener {
            makeToast(context, member.fullName + " upisan u klub")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}

fun updateMember(member: Member, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val memberReference = databaseReference.child("members").child(member.id)
    member.id = memberReference.key.toString()

    memberReference.setValue(member)
        .addOnSuccessListener {
            makeToast(context, "Uspješan update članarine")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}


