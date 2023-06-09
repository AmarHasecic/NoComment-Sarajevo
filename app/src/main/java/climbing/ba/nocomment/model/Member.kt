package climbing.ba.nocomment.model

data class Member(
    val fullName: String = "",
    var payments: List<Payment> = emptyList()
) {
    constructor() : this("", emptyList())
}