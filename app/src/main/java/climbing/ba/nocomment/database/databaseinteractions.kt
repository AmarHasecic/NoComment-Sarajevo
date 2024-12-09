package climbing.ba.nocomment.database

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.User
import climbing.ba.nocomment.sealed.DataState
import com.google.firebase.database.FirebaseDatabase
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

fun deleteMember(member: Member, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val memberReference = databaseReference.child("members").child(member.id)

    memberReference.removeValue()
        .addOnSuccessListener {
            makeToast(context, "Član obrisan")

        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}

suspend fun fetchMemberById(memberId: String): DataState {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val memberReference = databaseReference.child("members").child(memberId)

    return try {
        val dataSnapshot = memberReference.get().await()

        if (dataSnapshot.exists()) {
            val member = dataSnapshot.getValue(Member::class.java)
            if (member != null) {
                DataState.SuccessMember(member)
            } else {
                DataState.Empty
            }
        } else {
            DataState.Empty
        }
    } catch (e: Exception) {
        DataState.Failure(e.message ?: "An error occurred")
    }
}

suspend fun fetchUsers(): DataState {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val usersReference = databaseReference.child("users")

    val userList = mutableListOf<User>()

    return try {
        val dataSnapshot = usersReference.get().await()
        if (dataSnapshot.exists()) {
            for (childSnapshot in dataSnapshot.children) {
                val user = childSnapshot.getValue(User::class.java)
                user?.let {
                    userList.add(it)
                }
            }
            DataState.SuccessUsers(userList)
        } else {
            DataState.Empty
        }
    } catch (e: Exception) {
        DataState.Failure(e.message ?: "An error occurred")
    }
}

fun updateUserPassword(id: String, newPassword: String, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val userReference = databaseReference.child("users").child(id)

    val updates = mapOf<String, Any>(
        "password" to newPassword
    )

    userReference.updateChildren(updates)
        .addOnSuccessListener {
            makeToast(context, "Password successfully updated")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}







