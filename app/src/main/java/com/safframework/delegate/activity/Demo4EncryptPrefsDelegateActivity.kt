package com.safframework.delegate.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.prefs.EncryptPrefsHelper
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.delegate.activity.Demo4EncryptPrefsDelegateActivity.java
 * @author: Tony Shen
 * @date: 2018-06-13 23:21
 * @version V1.0 <描述当前版本功能>
 */
class Demo4EncryptPrefsDelegateActivity: AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    private fun initData() {

        val helper = EncryptPrefsHelper(getSharedPreferences("sp_encrypt" , Context.MODE_PRIVATE))

        helper.name = "tony"
        helper.password = "1234abcd"
        helper.age = 20
        helper.isForeigner = false

        L.i(helper.name)
        L.i(""+helper.age)
    }
}