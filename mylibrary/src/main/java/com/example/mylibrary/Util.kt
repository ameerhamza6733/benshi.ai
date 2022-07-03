package com.example.mylibrary

import java.math.BigInteger
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

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

}