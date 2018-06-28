package com.safframework.delegate.prefs

import android.content.SharedPreferences

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.PrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-28 23:43
 * @version V1.0 <描述当前版本功能>
 */
class PrefsHelper(prefs: SharedPreferences) {

    var name by prefs.string("name")

    var password by prefs.string("password")

    var age by prefs.int("age")

    var isForeigner by prefs.boolean("isForeigner")
}