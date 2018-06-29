package com.safframework.delegate.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.domain.User
import com.safframework.delegate.prefs.ObjectPrefsHelper
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.delegate.activity.Demo4ObjectPrefsDelegateActivity.java
 * @author: Tony Shen
 * @date: 2018-06-29 11:55
 * @version V1.0 <描述当前版本功能>
 */
class Demo4ObjectPrefsDelegateActivity: AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    private fun initData() {

        val helper = ObjectPrefsHelper(getSharedPreferences("sp_object" , Context.MODE_PRIVATE))

        val user1 = User("tony","123456789")

        val user2 = User("Monica","000000000")


        helper.user1 = user1
        helper.user2 = user2

        L.json(helper.user1)

        L.json(helper.user2)
    }
}