package com.mobisy.claims.utils

import android.os.Build

object VersionUtils {

    /**
     * Check android version is marshmallow
     */
    fun isGreaterThanM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     * Check android version is nougat
     */
    fun isGreaterThanN(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    /**
     * Check android version is oreo
     */
    fun isGreaterThanO(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    /**
     * Check android version is P
     */
    fun isGreaterThanP(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    /**
     * Check android version is Q
     */
    fun isGreaterThanQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

}