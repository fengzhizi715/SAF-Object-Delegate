package com.safframework.delegate.prefs

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.Prefs.kt
 * @author: Tony Shen
 * @date: 2018-06-11 19:03
 * @version V1.0 <描述当前版本功能>
 */
private inline fun <T> SharedPreferences.delegate(
        key: String? = null,
        defaultValue: T,
        crossinline getter: SharedPreferences.(String, T) -> T,
        crossinline setter: Editor.(String, T) -> Editor
): ReadWriteProperty<Any, T> =
        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T =
                    getter(key ?: property.name, defaultValue)!!

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                    edit().setter(key ?: property.name, value).apply()
        }

fun SharedPreferences.int(key: String? = null, defValue: Int = 0, isEncrypt:Boolean=false): ReadWriteProperty<Any, Int> {

    if (isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptInt, Editor::putEncryptInt)
    } else {

        return delegate(key, defValue, SharedPreferences::getInt, Editor::putInt)
    }
}

fun SharedPreferences.long(key: String? = null, defValue: Long = 0, isEncrypt:Boolean=false): ReadWriteProperty<Any, Long> {

    if (isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptLong, Editor::putEncryptLong)
    } else {

        return delegate(key, defValue, SharedPreferences::getLong, Editor::putLong)
    }
}

fun SharedPreferences.float(key: String? = null, defValue: Float = 0f, isEncrypt:Boolean=false): ReadWriteProperty<Any, Float> {

    if(isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptFloat, Editor::putEncryptFloat)
    } else {

        return delegate(key, defValue, SharedPreferences::getFloat, Editor::putFloat)
    }
}

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false, isEncrypt:Boolean=false): ReadWriteProperty<Any, Boolean> {

    if(isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptBoolean, Editor::putEncryptBoolean)
    } else {

        return delegate(key, defValue, SharedPreferences::getBoolean, Editor::putBoolean)
    }
}


fun SharedPreferences.stringSet(key: String? = null, defValue: Set<String> = emptySet(), isEncrypt:Boolean=false): ReadWriteProperty<Any, Set<String>> {

    if (isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptStringSet, Editor::putEncryptStringSet)
    } else {

        return delegate(key, defValue, SharedPreferences::getStringSet, Editor::putStringSet)
    }
}

fun SharedPreferences.string(key: String? = null, defValue: String = "", isEncrypt:Boolean=false): ReadWriteProperty<Any, String> {

    if (isEncrypt) {

        return delegate(key, defValue, SharedPreferences::getEncryptString, Editor::putEncryptString)
    } else {

        return delegate(key, defValue, SharedPreferences::getString, Editor::putString)
    }
}

fun SharedPreferences.initKey(key:String) = EncryptUtils.getInstance().key(key)


fun SharedPreferences.getEncryptInt(key: String, defValue: Int): Int {
    val encryptValue = this.getString(encryptPreference(key), null)
            ?: return defValue
    return Integer.parseInt(decryptPreference(encryptValue))
}

fun Editor.putEncryptInt(key: String, value: Int): Editor {
    this.putString(encryptPreference(key), encryptPreference(Integer.toString(value)))
    return this
}


fun SharedPreferences.getEncryptLong(key: String, defValue: Long): Long {
    val encryptValue = this.getString(encryptPreference(key), null)
            ?: return defValue
    return java.lang.Long.parseLong(decryptPreference(encryptValue))
}

fun Editor.putEncryptLong(key: String, value: Long): Editor {
    this.putString(encryptPreference(key), encryptPreference(java.lang.Long.toString(value)))
    return this
}


fun SharedPreferences.getEncryptFloat(key: String, defValue: Float): Float {
    val encryptValue = this.getString(encryptPreference(key), null)
            ?: return defValue
    return java.lang.Float.parseFloat(decryptPreference(encryptValue))
}

fun Editor.putEncryptFloat(key: String, value: Float): Editor {
    this.putString(encryptPreference(key), encryptPreference(java.lang.Float.toString(value)))
    return this
}


fun SharedPreferences.getEncryptBoolean(key: String, defValue: Boolean): Boolean {
    val encryptValue = this.getString(encryptPreference(key), null)
            ?: return defValue
    return java.lang.Boolean.parseBoolean(decryptPreference(encryptValue))
}

fun Editor.putEncryptBoolean(key: String, value: Boolean): SharedPreferences.Editor {
    this.putString(encryptPreference(key), encryptPreference(java.lang.Boolean.toString(value)))
    return this
}


fun SharedPreferences.getEncryptString(key: String, defValue: String?) : String {
    val encryptValue = this.getString(encryptPreference(key), null)
    return if (encryptValue == null) defValue?:"" else decryptPreference(encryptValue)
}

fun Editor.putEncryptString(key: String, value: String) : Editor {
    this.putString(encryptPreference(key), encryptPreference(value))
    return this
}


fun SharedPreferences.getEncryptStringSet(key: String, defValues: Set<String>): Set<String> {
    val encryptSet = this.getStringSet(encryptPreference(key), null)
            ?: return defValues
    val decryptSet = HashSet<String>()
    for (encryptValue in encryptSet) {
        decryptSet.add(decryptPreference(encryptValue))
    }
    return decryptSet
}

fun Editor.putEncryptStringSet(key: String, values: Set<String>): Editor {
    val encryptSet = HashSet<String>()
    for (value in values) {
        encryptSet.add(encryptPreference(value))
    }
    this.putStringSet(encryptPreference(key), encryptSet)
    return this
}

/**
 * encrypt function
 * @return cipherText base64
 */
private fun encryptPreference(plainText: String): String {

    return EncryptUtils.getInstance().encrypt(plainText)
}

/**
 * decrypt function
 * @return plainText
 */
private fun decryptPreference(cipherText: String): String {
    return EncryptUtils.getInstance().decrypt(cipherText)
}