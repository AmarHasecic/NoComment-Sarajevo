package climbing.ba.nocomment.sealed

import climbing.ba.nocomment.model.Member
import climbing.ba.nocomment.model.TenSessionCard
import climbing.ba.nocomment.model.User

sealed class DataState {
    class Success(val data: MutableList<Member>) : DataState()
    class SuccessMember(val data: Member) : DataState()
    class SuccessUsers(val data: MutableList<User>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
    class SuccessCards(val data: MutableList<TenSessionCard>) : DataState()
}