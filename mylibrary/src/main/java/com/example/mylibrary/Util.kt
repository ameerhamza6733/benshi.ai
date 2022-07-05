package com.example.mylibrary

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*


object Util {

    suspend fun getSHA(input: String): ByteArray? {
        // Static getInstance method is called with hashing SHA
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.toByteArray(Charset.defaultCharset()))
    }

    suspend fun toHexString(hash: ByteArray?): String? {
        // Convert byte array into signum representation
        val number = BigInteger(1, hash)

        // Convert message digest into hex value
        val hexString: StringBuilder = StringBuilder(number.toString(16))

        // Pad with leading zeros
        while (hexString.length < 64) {
            hexString.insert(0, '0')
        }
        return hexString.toString()
    }
    private var uniqueID: String? = null
    private const val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    @Synchronized
    fun id(context: Context): String? {
        if (uniqueID == null) {
            val sharedPrefs: SharedPreferences = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE
            )
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                val editor: SharedPreferences.Editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.commit()
            }
        }
        return uniqueID
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}