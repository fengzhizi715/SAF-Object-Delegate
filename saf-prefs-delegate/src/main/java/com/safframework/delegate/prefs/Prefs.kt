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

fun SharedPreferences.int(key: String? = null, defValue: Int = 0): ReadWriteProperty<Any, Int> =
        delegate(key, defValue, SharedPreferences::getInt, Editor::putInt)

fun SharedPreferences.long(key: String? = null, defValue: Long = 0): ReadWriteProperty<Any, Long> =
        delegate(key, defValue, SharedPreferences::getLong, Editor::putLong)

fun SharedPreferences.float(key: String? = null, defValue: Float = 0f): ReadWriteProperty<Any, Float> =
        delegate(key, defValue, SharedPreferences::getFloat, Editor::putFloat)

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false): ReadWriteProperty<Any, Boolean> =
        delegate(key, defValue, SharedPreferences::getBoolean, Editor::putBoolean)

fun SharedPreferences.stringSet(key: String? = null, defValue: Set<String> = emptySet()): ReadWriteProperty<Any, Set<String>> =
        delegate(key, defValue, SharedPreferences::getStringSet, Editor::putStringSet)

fun SharedPreferences.string(key: String? = null, defValue: String = ""): ReadWriteProperty<Any, String> =
        delegate(key, defValue, SharedPreferences::getString, Editor::putString)