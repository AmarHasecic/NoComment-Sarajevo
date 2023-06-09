package climbing.ba.nocomment.sealed

import climbing.ba.nocomment.model.Member

sealed class DataState {
    class Success(val data: MutableList<Member>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}