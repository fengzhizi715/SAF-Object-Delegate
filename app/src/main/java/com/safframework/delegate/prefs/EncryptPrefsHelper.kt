package com.safframework.delegate.prefs

import android.content.SharedPreferences


/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.EncryptPrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-13 23:44
 * @version V1.0 <描述当前版本功能>
 */
class EncryptPrefsHelper(prefs: SharedPreferences) {

    init {

        prefs.initKey("12345678910abcde") // 初始化密钥，且密钥是16位的
    }

    var name by prefs.string("name",isEncrypt=true)

    var password by prefs.string("password",isEncrypt=true)

    var age by prefs.int("age",isEncrypt=true)

    var isForeigner by prefs.boolean("isForeigner",isEncrypt=true)
}