package climbing.ba.nocomment.model

data class Member(
    var id: String = "",
    val fullName: String = "",
    var payments: List<Payment> = emptyList()
) {
    constructor() : this("", "",emptyList())
}