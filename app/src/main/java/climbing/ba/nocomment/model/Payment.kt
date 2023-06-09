package climbing.ba.nocomment.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Month

data class Payment(
    val amount: Int,
    val month: Month
){
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this(0, Month.JANUARY)
}
