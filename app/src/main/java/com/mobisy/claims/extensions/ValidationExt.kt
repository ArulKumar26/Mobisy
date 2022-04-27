package com.mobisy.claims.extensions

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.trim() == "" || value.isEmpty() || value.equals(
        "null",
        ignoreCase = true
    )



