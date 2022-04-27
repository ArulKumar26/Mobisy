package com.mobisy.claims.extensions

import android.util.Patterns

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.isEmpty() || value.equals("null", ignoreCase = true)

fun isValidEmail(email: String?): Boolean =
    email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()


