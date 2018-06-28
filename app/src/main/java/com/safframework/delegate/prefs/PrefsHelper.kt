package com.safframework.delegate.prefs

import android.content.SharedPreferences
import com.safframework.delegate.domain.User


/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.PrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-13 23:44
 * @version V1.0 <描述当前版本功能>
 */
class PrefsHelper(prefs: SharedPreferences) {

    init {

        prefs.initKey("12345678910abcde")
    }

    var user by prefs.gson<User?>(null)

    var name by prefs.string("name",isEncrypt=true)
}