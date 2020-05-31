package dev.farouk.depensy.core.util.extensions

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by Farouk on 30/05/20.
 */
fun Fragment.changeStatusBarColor(@ColorRes color: Int) {
    requireNotNull(activity).window.statusBarColor =
            ContextCompat.getColor(requireNotNull(context), color)
}