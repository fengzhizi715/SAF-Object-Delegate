package com.safframework.delegate.prefs

import android.content.SharedPreferences
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.PrefsFastJSON.kt
 * @author: Tony Shen
 * @date: 2018-06-11 19:54
 * @version V1.0 <描述当前版本功能>
 */
inline fun <reified T> SharedPreferences.json(defaultValue: T,key: String? = null) =
        object : ReadWriteProperty<Any, T> {

            override fun getValue(thisRef: Any, property: KProperty<*>): T {

                val s = getString(key ?: property.name, "")

                return if (s.isBlank()) defaultValue else JSON.parseObject(s, T::class.java)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                    edit().putString(key ?: property.name, JSON.toJSONString(value)).apply()
        }

inline fun <reified T> SharedPreferences.jsonList(key: String? = null) =
        object : ReadWriteProperty<Any, List<T>> {

            override fun getValue(thisRef: Any, property: KProperty<*>): List<T> {

                val s = getString(key ?: property.name, "")

                return if (s.isBlank()) emptyList() else JSON.parseArray(s,T::class.java)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>) =
                edit().putString(key ?: property.name, JSONObject.toJSONString(value, SerializerFeature.WriteClassName)).apply()
        }