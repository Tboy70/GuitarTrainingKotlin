package thomas.guitartrainingkotlin.domain.exception

open class BaseException : Exception {

    val code: Int

    constructor() : super() {
        code = CODE_NONE
    }

    constructor(code: Int) : super() {
        this.code = code
    }

    constructor(message: String, code: Int) : super(message) {
        this.code = code
    }

    companion object {
        const val CODE_NONE = 100
        const val CODE_MODULE_TRACE_WRITE = 101
    }

}