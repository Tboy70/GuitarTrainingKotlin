package thomas.guitartrainingkotlin.data.exception.mapper

import thomas.guitartrainingkotlin.domain.exception.BaseException

class DataMappingException : BaseException {
    constructor() : super()

    constructor(code: Int) : super(code)

    constructor(message: String, code: Int) : super(message, code)
}