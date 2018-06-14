package com.safframework.delegate.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.prefs.PrefsHelper
import com.safframework.delegate.prefs.string
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.delegate.activity.Demo4PrefsDelegateActivity.java
 * @author: Tony Shen
 * @date: 2018-06-13 23:21
 * @version V1.0 <描述当前版本功能>
 */
class Demo4PrefsDelegateActivity: AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    private fun initData() {

        val helper = PrefsHelper(PreferenceManager.getDefaultSharedPreferences(this))

        helper.saveToken("11111")

        L.i(helper.token)
    }
}