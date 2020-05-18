package dev.farouk.depensy.core.util.extensions

import android.content.res.Resources

/**
 * Created by Farouk on 17/05/20.
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()