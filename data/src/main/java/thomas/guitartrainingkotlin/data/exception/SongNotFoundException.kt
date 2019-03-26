package thomas.guitartrainingkotlin.data.exception

import thomas.guitartrainingkotlin.domain.exception.BaseException

class SongNotFoundException : BaseException {
    constructor() : super()

    constructor(code: Int) : super(code)

    constructor(message: String, code: Int) : super(message, code)
}