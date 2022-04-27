package com.mobisy.claims.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * Method to get fragment by tag. The operation is performed by the supportFragmentManager.
 */
fun AppCompatActivity.findFragmentByTag(tag: String): Fragment? {
    return supportFragmentManager.findFragmentByTag(tag)
}

/**
 * Setup actionbar
 */
fun AppCompatActivity.setupActionBar(toolbarId: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbarId)
    supportActionBar?.run {
        setDisplayHomeAsUpEnabled(true)
        setDisplayShowHomeEnabled(true)
        action()
    }
}

/**
 * AppCompatActivity's toolbar visibility modifiers
 */
fun AppCompatActivity.hideToolbar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.showToolbar() {
    supportActionBar?.show()
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (options != null) {
        intent.putExtras(options)
    }
    startActivity(intent)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)