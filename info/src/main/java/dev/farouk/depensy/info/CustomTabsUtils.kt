package dev.farouk.depensy.info

import android.content.Context
import android.net.Uri
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.getColorOrThrow

object CustomTabsUtils {

    fun launchCustomTabs(context: Context, url: String) {
        // primary color
        val color = context.getColorPrimary()
        CustomTabsIntent.Builder()
                .setToolbarColor(color)
                .enableUrlBarHiding()
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .build()
                .launchUrl(context, Uri.parse(url))
    }
}

@ColorInt
fun Context.getColorPrimary() = getAttrColor(android.R.attr.colorPrimary)

@ColorInt
private fun Context.getAttrColor(@AttrRes attrColor: Int) =
        theme.obtainStyledAttributes(intArrayOf(attrColor)) .getColorOrThrow(0)
