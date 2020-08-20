package thomas.guitartrainingkotlin.domain.exception

open class BaseException : Exception() {

    private val code: Int

    init {
        code = CODE_NONE
    }
    companion object {
        const val CODE_NONE = 100
    }
}