package dev.farouk.depensy.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dev.farouk.depensy.data.models.AuthUser

/**
 * Created by farouk on 12/09/19.
 */
internal class AuthServiceImpl: AuthService {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun addStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.addAuthStateListener(listener)
    }

    override suspend fun createUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signIn(email: String, password: String): String {
        return firebaseAuth.signInWithEmailAndPassword(email, password).toString()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override suspend fun setUserDisplayName(name: String) {
        val updateProfile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        firebaseAuth.currentUser?.updateProfile(updateProfile)
    }

    override fun getCurrentUser(): AuthUser {
        return AuthUser(
                firebaseAuth.currentUser!!.uid,
                firebaseAuth.currentUser!!.email ?: "",
                firebaseAuth.currentUser!!.displayName ?: ""
        )
    }

    override fun isSignedIn(): Boolean = firebaseAuth.currentUser != null

    override fun logOut() {
        firebaseAuth.signOut()
    }
}