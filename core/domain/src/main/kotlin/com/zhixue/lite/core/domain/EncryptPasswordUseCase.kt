package com.zhixue.lite.core.domain

import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher
import javax.inject.Inject

private const val RSA_ALGORITHM = "RSA"
private const val RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding"
private const val KEY_MODULES =
    "00ccd806a03c7391ee8f884f5902102d95f6d534d597ac42219dd8a79b1465e186c0162a6771b55e7be7422c4af494ba0112ede4eb00fc751723f2c235ca419876e7103ea904c29522b72d754f66ff1958098396f17c6cd2c9446e8c2bb5f4000a9c1c6577236a57e270bef07e7fe7bbec1f0e8993734c8bd4750e01feb21b6dc9"
private const val KEY_PUBLIC_EXPONENT = "010001"

class EncryptPasswordUseCase @Inject constructor() {

    private val _keySpec = RSAPublicKeySpec(
        BigInteger(KEY_MODULES.hexToBytes()),
        BigInteger(KEY_PUBLIC_EXPONENT.hexToBytes())
    )
    private val _publicKey = KeyFactory
        .getInstance(RSA_ALGORITHM)
        .generatePublic(_keySpec)
    private val _cipher = Cipher.getInstance(RSA_TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, _publicKey)
    }

    operator fun invoke(password: String): String {
        return runCatching {
            _cipher.doFinal(password.toByteArray()).toHex()
        }.getOrDefault(
            defaultValue = ""
        )
    }
}

private fun String.hexToBytes(): ByteArray {
    return chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}

private fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}