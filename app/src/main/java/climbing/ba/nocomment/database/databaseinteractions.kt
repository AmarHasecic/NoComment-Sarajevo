package climbing.ba.nocomment.database

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.TenSessionCard
import climbing.ba.nocomment.model.User
import climbing.ba.nocomment.sealed.DataState
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate


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
                member?.let { m ->
                    memberList.add(m)
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
    val memberReference = databaseReference.child("members").push()
    val memberId = memberReference.key

    if (memberId != null) {
        member.id = memberId
    }

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

fun addTenSessionCard(card: TenSessionCard, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val cardReference = databaseReference.child("tenSessionCards").push()
    val cardId = cardReference.key

    if (cardId != null) {
        card.id = cardId
    }

    cardReference.setValue(card)
        .addOnSuccessListener {
            makeToast(context, "Kartica uspješno kreirana")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}

fun updateTenSessionCard(card: TenSessionCard, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val cardReference = databaseReference.child("tenSessionCards").child(card.id)

    cardReference.setValue(card)
        .addOnSuccessListener {
            makeToast(context, "Kartica uspješno ažurirana")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}

fun deleteTenSessionCard(card: TenSessionCard, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val cardReference = databaseReference.child("tenSessionCards").child(card.id)

    cardReference.removeValue()
        .addOnSuccessListener {
            makeToast(context, "Kartica obrisana")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}
suspend fun fetchTenSessionCards(): DataState {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val cardsReference = databaseReference.child("tenSessionCards")

    val cardList = mutableListOf<TenSessionCard>()

    return try {
        val dataSnapshot = cardsReference.get().await()

        if (dataSnapshot.exists()) {
            for (childSnapshot in dataSnapshot.children) {
                val card = childSnapshot.getValue(TenSessionCard::class.java)
                card?.let { cardList.add(it) }
            }
            DataState.SuccessCards(cardList)
        } else {
            DataState.Empty
        }
    } catch (e: Exception) {
        DataState.Failure(e.message ?: "An error occurred")
    }
}

fun archiveTenSessionCard(card: TenSessionCard, context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().reference
    val cardReference = databaseReference
        .child("tenSessionCards")
        .child(card.id)

    val updates = mapOf<String, Any>(
        "archived" to true
    )
    cardReference.updateChildren(updates)
        .addOnSuccessListener {
            makeToast(context, "Kartica arhivirana ✅")
        }
        .addOnFailureListener { exception ->
            makeToast(context, "Error: ${exception.message}")
        }
}

