package com.safframework.delegate.prefs

import android.content.SharedPreferences
import com.safframework.delegate.domain.User

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.ObjectPrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-29 11:56
 * @version V1.0 <描述当前版本功能>
 */
class ObjectPrefsHelper(prefs: SharedPreferences) {

    var user1 by prefs.json<User?>(null)

    var user2 by prefs.gson<User?>(null)

}