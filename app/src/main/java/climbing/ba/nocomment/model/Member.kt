package climbing.ba.nocomment.model


data class Member(
    var id: String = "",
    val fullName: String = "",
    var imeRoditelja: String = "",
    var brojTelefonaRoditelja: String = "",
    var payments: List<Payment> = emptyList(),
    val type: MemberType? = null
) {
    constructor() : this("", "", "", "",emptyList(), null)
}
