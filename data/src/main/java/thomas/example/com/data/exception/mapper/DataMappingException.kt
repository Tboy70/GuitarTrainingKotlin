package thomas.example.com.data.exception.mapper

import thomas.example.com.exception.BaseException

class DataMappingException : BaseException {
    constructor() : super()

    constructor(code: Int) : super(code)

    constructor(message: String, code: Int) : super(message, code)
}