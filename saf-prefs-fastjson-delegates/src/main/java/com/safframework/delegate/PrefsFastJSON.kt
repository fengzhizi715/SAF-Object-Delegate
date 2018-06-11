package com.safframework.delegate

import android.content.SharedPreferences
import com.alibaba.fastjson.JSON
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * @FileName:
 *          com.safframework.delegate.PrefsFastJSON.java
 * @author: Tony Shen
 * @date: 2018-06-11 19:54
 * @version V1.0 <描述当前版本功能>
 */
inline fun <reified T> SharedPreferences.json(key: String? = null, defaultValue: T) =
        object : ReadWriteProperty<Any, T> {

            override fun getValue(thisRef: Any, property: KProperty<*>): T {

                val s = getString(key ?: property.name, "")

                return if (s.isBlank()) defaultValue else JSON.parseObject(s, T::class.java)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                edit().putString(key ?: property.name, JSON.toJSONString(value)).apply()
            }
        }