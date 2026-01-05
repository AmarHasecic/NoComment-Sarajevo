package climbing.ba.nocomment.scripts

import climbing.ba.nocomment.model.Payment
import com.google.firebase.database.FirebaseDatabase
import java.time.Month
import java.util.Calendar

data class PaymentWithYear(
    var amount: Int,
    val month: Month,
    val year: Int
)

fun addYearToAllPayments() {
    val databaseReference = FirebaseDatabase.getInstance().reference.child("members")
    val defaultYear = Calendar.getInstance().get(Calendar.YEAR)

    databaseReference.get().addOnSuccessListener { snapshot ->
        snapshot.children.forEach { memberSnapshot ->
            val memberId = memberSnapshot.key ?: return@forEach

            val payments = memberSnapshot.child("payments").children.mapNotNull {
                it.getValue(Payment::class.java)
            }

            val updatedPayments = payments.map { payment ->
                PaymentWithYear(
                    amount = payment.amount,
                    month = payment.month,
                    year = defaultYear
                )
            }

            databaseReference.child(memberId).child("payments").setValue(updatedPayments)
                .addOnSuccessListener {
                    println("Updated payments for member $memberId")
                }
                .addOnFailureListener { e ->
                    println("Failed to update member $memberId: ${e.message}")
                }
        }
    }.addOnFailureListener { e ->
        println("Failed to fetch members: ${e.message}")
    }
}
