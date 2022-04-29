package com.mobisy.claims.data.model

class CustomException() : Exception() {
    var code: Int = 0
    var error: String = ""

    constructor(code: Int, error: String) : this() {
        this.code = code
        this.error = error
    }
}

