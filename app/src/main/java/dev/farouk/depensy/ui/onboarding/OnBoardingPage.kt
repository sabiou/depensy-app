package dev.farouk.depensy.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.farouk.depensy.R

/**
 * Created by Farouk on 30/05/20.
 */
enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val subTitleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val logoResource: Int
) {
    ONE(R.string.onboarding_slide1_title, R.string.onboarding_slide1_subtitle, R.string.onboarding_slide1_desc, R.drawable.ic_receipt),
    TWO(R.string.onboarding_slide2_title, R.string.onboarding_slide2_subtitle, R.string.onboarding_slide2_desc, R.drawable.ic_investing),
    THREE(R.string.onboarding_slide3_title, R.string.onboarding_slide3_subtitle, R.string.onboarding_slide3_desc, R.drawable.ic_savings),
}