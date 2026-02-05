package climbing.ba.nocomment.model

data class Session(
    var isUsed: Boolean = false,
    var date: String? = null
)

data class TenSessionCard(
    var id: String = "",
    val memberName: String = "",
    var sessions: List<Session> = List(10) { Session() },
    var archived: Boolean = false
) {
    constructor() : this("", "", List(10) { Session() }, false)
}

