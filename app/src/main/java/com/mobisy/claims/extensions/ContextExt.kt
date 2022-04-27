package com.mobisy.claims.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.mobisy.claims.BuildConfig

fun Activity.logd(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

fun Activity.loge(message: String){
    if (BuildConfig.DEBUG) Log.e(this::class.java.simpleName, message)
}

fun Context.getStringRes(resId: Int) =
    resources.getString(resId)

fun Context.getInteger(@IntegerRes resId: Int) =
    resources.getInteger(resId)

fun Context.getDimenSize(@DimenRes resId: Int) =
    resources.getDimensionPixelSize(resId)

fun Context.getCompatColor(@ColorRes resId: Int) =
    ContextCompat.getColor(this, resId)

fun Context.getCompatDrawable(drawableId: Int) =
    ContextCompat.getDrawable(this, drawableId)

fun Context.showMessage(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.showMessage(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}

