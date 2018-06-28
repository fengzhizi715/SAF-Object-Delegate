package com.safframework.delegate.prefs

import android.text.TextUtils
import android.os.Build
import android.provider.Settings;
import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.EncryptUtils.java
 * @author: Tony Shen
 * @date: 2018-06-28 02:01
 * @version V1.0 <描述当前版本功能>
 */
class EncryptUtils private constructor(context: Context) {

    private val key: String

    init {
        key = getDeviceSerialNumber(context)
    }

    /**
     * Gets the hardware serial number of this device.
     *
     * @return serial number or Settings.Secure.ANDROID_ID if not available.
     */
    @SuppressLint("HardwareIds")
    private fun getDeviceSerialNumber(context: Context): String {
        // We're using the Reflection API because Build.SERIAL is only available
        // since API Level 9 (Gingerbread, Android 2.3).
        try {
            val deviceSerial = Build::class.java.getField("SERIAL").get(null) as String
            return if (TextUtils.isEmpty(deviceSerial)) {
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
            } else {
                deviceSerial
            }
        } catch (ignored: Exception) {
            // Fall back  to Android_ID
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        }

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

    companion object {

        private var instance: EncryptUtils? = null

        fun getInstance(context: Context): EncryptUtils =
                instance ?: synchronized(this) {
                    instance ?: EncryptUtils(context).also { instance = it }
                }
    }
}