package climbing.ba.nocomment.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Month
import java.time.Year

data class Payment(
    var amount: Int,
    val month: Month,
    val year: Int
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this(
        amount = 0,
        month = Month.JANUARY,
        year = Year.now().value
    )
}
