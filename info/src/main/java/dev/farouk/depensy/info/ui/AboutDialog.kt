package dev.farouk.depensy.info.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import dev.farouk.depensy.info.R

class AboutDialog: DialogFragment() {

    companion object {
        // TAG
        private const val TAG = "ABOUT_DIALOG"

        // show the dialog inside of the context
        fun show(context: InfoFragment) {
            val dialog = AboutDialog()

            dialog.show(
                    context.childFragmentManager,
                    TAG
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity ?: throw IllegalStateException("ohh pas ça")

        return MaterialDialog(context)
                .title(text = getString(R.string.about_title))
                .message(
                        res = R.string.about_body,
                        html = true,
                        lineHeightMultiplier = 1.4f
                )
    }
}