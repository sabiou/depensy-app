package com.godilabs.depensy.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferencesManager(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "user_preferences",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_USERNAME = "username"
        private const val DEFAULT_USERNAME = "Utilisateur"
    }

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, DEFAULT_USERNAME) ?: DEFAULT_USERNAME
    }

    fun setUsername(username: String) {
        sharedPreferences.edit()
            .putString(KEY_USERNAME, username.trim().ifBlank { DEFAULT_USERNAME })
            .apply()
    }

    fun hasCustomUsername(): Boolean {
        return sharedPreferences.contains(KEY_USERNAME) &&
                getUsername() != DEFAULT_USERNAME
    }
}