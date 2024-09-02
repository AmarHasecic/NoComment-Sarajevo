package climbing.ba.nocomment.model

enum class MemberType(val displayName: String) {
    JUNIOR("Junior"),
    NAPREDNI_JUNIOR("Napredni Junior"),
    STARIJA_GRUPA("Starija Grupa"),
    REKREATIVAC("Rekreativac"),
    PUBERTETLIJA("Pubertetlija");

    override fun toString(): String {
        return displayName
    }
}