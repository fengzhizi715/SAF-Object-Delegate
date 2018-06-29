package com.safframework.delegate.prefs

import android.util.Base64

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.EncryptUtils.kt
 * @author: Tony Shen
 * @date: 2018-06-28 02:01
 * @version V1.0 <描述当前版本功能>
 */
class EncryptUtils private constructor() {

    private lateinit var key:String

    /**
     * 必须要16位密钥匙
     */
    fun key(key:String) {

        this.key = key
    }

    /**
     * AES128加密
     * @param plainText 明文
     * @return
     */
    fun encrypt(plainText: String): String {

        try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keyspec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keyspec)
            val encrypted = cipher.doFinal(plainText.toByteArray())
            return Base64.encodeToString(encrypted, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }

    /**
     * AES128解密
     * @param cipherText 密文
     * @return
     */
    fun decrypt(cipherText: String): String {
        try {
            val encrypted1 = Base64.decode(cipherText, Base64.NO_WRAP)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keyspec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keyspec)
            val original = cipher.doFinal(encrypted1)
            return String(original)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private object mHolder {

        val instance = EncryptUtils()
    }

    companion object {

        fun getInstance(): EncryptUtils = mHolder.instance
    }
}